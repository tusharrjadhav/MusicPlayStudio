package music.com.musicplaystudio.activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.darwindeveloper.wcviewpager.WCViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

import music.com.musicplaystudio.PlayApplication;
import music.com.musicplaystudio.R;
import music.com.musicplaystudio.data.Audio;
import music.com.musicplaystudio.viewmodel.AudioViewModel;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class PlayListHomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public static final String TAG = PlayListHomeActivity.class.getSimpleName();
    private WCViewPagerIndicator wcViewPagerIndicator;
    private TextView emptyMsgTx;
    private TextView favTextView, historyTextView;
    public AudioViewModel viewModel;


    //region Activity Lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_playlist);
        emptyMsgTx = findViewById(R.id.empty_msg);
        favTextView = findViewById(R.id.openFav);
        historyTextView = findViewById(R.id.openHistory);

        wcViewPagerIndicator = findViewById(R.id.wcviewpager);
        wcViewPagerIndicator.getViewPager().addOnPageChangeListener(this);

        viewModel = PlayApplication.obtainViewModel(this);

        favTextView.setOnClickListener(onClickListener);
        historyTextView.setOnClickListener(onClickListener);

        subscribe();
    }
    //endregion


    //region OnPageChangeListener
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        wcViewPagerIndicator.setSelectedindicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    //endregion

    //region Custom Methods
    private void subscribe() {
        final Observer<List<Audio>> audioListObserver = audioList -> {
            Log.d(TAG, "On UI update");

            if (audioList == null || audioList.isEmpty()) {
                emptyMsgTx.setVisibility(View.VISIBLE);
                wcViewPagerIndicator.setVisibility(View.GONE);
            } else {
                emptyMsgTx.setVisibility(View.GONE);
                wcViewPagerIndicator.setVisibility(View.VISIBLE);
                MyAdapter mAdapter = new MyAdapter(getSupportFragmentManager(), audioList);
                wcViewPagerIndicator.setAdapter(mAdapter);
            }
        };
        viewModel.getAllAudioListLiveData().observe(this, audioListObserver);
        viewModel.getAudioListFromService();
    }
    //endregion

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(PlayListHomeActivity.this, PlayListActivity.class);
            intent.putExtra(PlayListActivity.EXTRA_TYPE, view == favTextView ? PlayListActivity.FAVORITE : PlayListActivity.HISTORY);
            startActivity(intent);
        }
    };

    /**
     * Inner static class
     */
    public static class MyAdapter extends FragmentPagerAdapter {

        List<Audio> audioList;

        MyAdapter(FragmentManager fm, List<Audio> trains) {
            super(fm);
            audioList = trains;
        }

        @Override
        public int getCount() {
            return (int) Math.ceil(audioList.size() / 3.0);
        }

        @Override
        public Fragment getItem(int position) {
            ArrayList<Audio> subAudios;
            int lastIndexForTrainList = position * 3 + 3;
            if (lastIndexForTrainList < audioList.size()) {
                subAudios = new ArrayList<>(audioList.subList(position * 3, lastIndexForTrainList));
            } else {
                subAudios = new ArrayList<>(audioList);
            }

            return PlayListFragment.instance(subAudios, position * 3);
        }


    }

}
