package music.com.musicplaystudio.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.List;

import music.com.musicplaystudio.PlayApplication;
import music.com.musicplaystudio.R;
import music.com.musicplaystudio.callback.GetAudioListCallBack;
import music.com.musicplaystudio.data.Audio;
import music.com.musicplaystudio.viewmodel.AudioViewModel;

public class MainActivity extends AppCompatActivity {

    List<Audio> audioList;

    ImageView collapsingImageView;

    AudioViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsingImageView = findViewById(R.id.collapsingImageView);

        viewModel = PlayApplication.obtainViewModel(this);

        getAudioList();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new PlayConnection().fetchPhotoItems();
//            }
//        }).start();

    }

    private void getAudioList() {
        viewModel.getAudioListFromService(new GetAudioListCallBack() {
            @Override
            public void onAudioLoaded(List<Audio> audioList) {
                MainActivity.this.audioList = audioList;
                updateAudioList();
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void updateAudioList() {
        if (audioList.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.recyclerview);
            MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(audioList, getApplication());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}
