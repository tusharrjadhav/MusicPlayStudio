package music.com.musicplaystudio.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import music.com.musicplaystudio.callback.GetAudioListCallBack;
import music.com.musicplaystudio.data.Audio;
import music.com.musicplaystudio.data.source.local.LocalRepository;
import music.com.musicplaystudio.data.source.remote.RemoteRepository;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class AudioViewModel extends ViewModel {

    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;
    private String TAG = AudioViewModel.class.getSimpleName();

    private MutableLiveData<List<Audio>> allAudioListLiveData = new MutableLiveData<>();

    public AudioViewModel(RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public void getAudioListFromService() {

        remoteRepository.getAudioList(new GetAudioListCallBack() {
            @Override
            public void onAudioLoaded(List<Audio> audioList) {
                allAudioListLiveData.postValue(audioList);
            }

            @Override
            public void onDataNotAvailable() {
                allAudioListLiveData.postValue(null);
            }
        });

    }

    public void getHistoryAudiodList() {
        localRepository.getAudioHistoryList(new GetAudioListCallBack() {
            @Override
            public void onAudioLoaded(List<Audio> audioList) {
                allAudioListLiveData.postValue(audioList);
            }

            @Override
            public void onDataNotAvailable() {
                allAudioListLiveData.postValue(null);
            }
        });
    }

    public void getFavoriteAudiodList() {
        localRepository.getFavoriteAudioList(new GetAudioListCallBack() {
            @Override
            public void onAudioLoaded(List<Audio> audioList) {
                allAudioListLiveData.postValue(audioList);
            }

            @Override
            public void onDataNotAvailable() {
                allAudioListLiveData.postValue(null);
            }
        });
    }

    public void updateAudioData(Audio audio) {
        localRepository.addAudio(audio);
    }

    public MutableLiveData<List<Audio>> getAllAudioListLiveData() {
        return allAudioListLiveData;
    }
}
