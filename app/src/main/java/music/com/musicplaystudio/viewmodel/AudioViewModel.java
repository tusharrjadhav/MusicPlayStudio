package music.com.musicplaystudio.viewmodel;

import android.arch.lifecycle.ViewModel;

import music.com.musicplaystudio.callback.GetAudioListCallBack;
import music.com.musicplaystudio.data.source.local.LocalRepository;
import music.com.musicplaystudio.data.source.remote.RemoteRepository;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class AudioViewModel extends ViewModel {

    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;
    private String TAG = AudioViewModel.class.getSimpleName();

    public AudioViewModel(RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    public void getAudioListFromService(final GetAudioListCallBack callback) {
        /**
         GET Page Resources
         **/
         remoteRepository.getAudioList(callback);

    }
}
