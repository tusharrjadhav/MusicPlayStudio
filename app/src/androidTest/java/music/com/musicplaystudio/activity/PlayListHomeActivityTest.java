package music.com.musicplaystudio.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import music.com.musicplaystudio.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class PlayListHomeActivityTest {

    private ViewInteraction favoriteTextInteraction;
    private ViewInteraction historyTextInteraction;

    @Rule
    public ActivityTestRule<PlayListHomeActivity> mActivityTestRule = new ActivityTestRule<>(PlayListHomeActivity.class);

    @Before
    public void playListHomeActivity() {
        Intents.init();

        favoriteTextInteraction = onView(
                allOf(withId(R.id.openFav), withText("Favorite"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));



        historyTextInteraction = onView(
                allOf(withId(R.id.openHistory), withText("History"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));

    }

    @After
    public void afterTask() throws Exception {
        Intents.release();
    }

    @Test
    public void testFavariteClick() throws Exception {
        favoriteTextInteraction.perform(click());

        addDelay();

        intended(hasComponent(PlayListActivity.class.getName()));

        pressBack();

    }

    @Test
    public void testHistoryClick() throws Exception {
        historyTextInteraction.perform(click());

        addDelay();

        intended(hasComponent(PlayListActivity.class.getName()));

        pressBack();

    }

    private void addDelay() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
