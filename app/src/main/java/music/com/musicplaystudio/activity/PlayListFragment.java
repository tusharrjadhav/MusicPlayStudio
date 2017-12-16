package music.com.musicplaystudio.activity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import music.com.musicplaystudio.R;
import music.com.musicplaystudio.data.Audio;
import music.com.musicplaystudio.viewmodel.AudioViewModel;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class PlayListFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {

    public static final String EXTRA_AUDIO = "EXTRA_AUDIO";
    public static final String EXTRA_AUDIO_INDEX = "EXTRA_AUDIO_INDEX";
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    MediaPlayer mediaPlayer;
    List<Audio> audioList;

    AudioViewModel viewModel;


    //region Constructor method
    public static PlayListFragment instance(ArrayList<Audio> audioArrayList, int index) {
        PlayListFragment fragment = new PlayListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_AUDIO, audioArrayList);
        bundle.putInt(EXTRA_AUDIO_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    //endregion

    //region Fragment Lifecycle
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_playlist, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        audioList = (List<Audio>) getArguments().getSerializable(EXTRA_AUDIO);
        updateUI();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null) {
            if (getActivity() instanceof PlayListHomeActivity) {

                viewModel = ((PlayListHomeActivity) getActivity()).viewModel;
            } else if (getActivity() instanceof PlayListActivity) {
                viewModel = ((PlayListActivity) getActivity()).viewModel;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopMediaPlayer();
    }

    //endregion


    //region custom methods
    private void updateUI() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyRecyclerViewAdapter(audioList, getContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void updateAudioData(Audio audio) {
        if(viewModel != null) {
            viewModel.updateAudioData(audio);
        }
    }

    private void playAudioSong(Audio audio) {

        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audio.getUrl());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }
    //endregion


    //region ItemClickListener
    @Override
    public void playAudio(View view, int position) {
        Audio audio = audioList.get(position);
        audio.setPlayed(!audio.isPlayed());
        updateAudioData(audio);
        stopMediaPlayer();
        playAudioSong(audio);
    }

    private void stopMediaPlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void downloadAudio(View view, int position) {

    }

    @Override
    public void favAudio(View view, int position) {
        Audio audio = audioList.get(position);
        audio.setFavorite(!audio.isFavorite());
        updateAudioData(audio);
        adapter.notifyItemChanged(position, audio);
    }


    //endregion
}
