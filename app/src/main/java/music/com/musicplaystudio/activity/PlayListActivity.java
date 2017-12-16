package music.com.musicplaystudio.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import music.com.musicplaystudio.PlayApplication;
import music.com.musicplaystudio.R;
import music.com.musicplaystudio.data.Audio;
import music.com.musicplaystudio.viewmodel.AudioViewModel;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class PlayListActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "EXTRA_TYPE";

    public static final String HISTORY = "history";
    public static final String FAVORITE = "favorite";

    private String audioListType = HISTORY;


    private static final String TAG = PlayListActivity.class.getSimpleName();
    public AudioViewModel viewModel;
    private TextView emptyTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);
        emptyTextView = findViewById(R.id.empty_msg);

        viewModel = PlayApplication.obtainViewModel(this);

        subscribe();

        audioListType = getIntent().getStringExtra(EXTRA_TYPE);

        if (audioListType.equals(HISTORY)) {
            setTitle(getString(R.string.title_history));
            viewModel.getHistoryAudiodList();
        } else {
            setTitle(getString(R.string.title_favorite));
            viewModel.getFavoriteAudiodList();
        }
    }

    private void openPlayList(ArrayList<Audio> audioArrayList) {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.playList, PlayListFragment.instance(audioArrayList, 0));

        fragmentTransaction.commit();
    }


    //region Custom Methods
    private void subscribe() {
        final Observer<List<Audio>> audioListObserver = audioList -> {
            Log.d(TAG, "On UI update");

            if (audioList == null || audioList.isEmpty()) {
                emptyTextView.setVisibility(View.VISIBLE);
            } else {
                emptyTextView.setVisibility(View.GONE);
                openPlayList(new ArrayList<>(audioList));
            }
        };
        viewModel.getAllAudioListLiveData().observe(this, audioListObserver);
    }
    //endregion

}
