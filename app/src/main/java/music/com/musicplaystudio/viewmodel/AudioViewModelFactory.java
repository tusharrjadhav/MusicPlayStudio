package music.com.musicplaystudio.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import music.com.musicplaystudio.data.source.local.LocalRepository;
import music.com.musicplaystudio.data.source.remote.RemoteRepository;


public class AudioViewModelFactory implements ViewModelProvider.Factory {

    private static AudioViewModelFactory INSTANCE;

    private RemoteRepository remoteRepository;
    private LocalRepository localRepository;

    public static void initialize(RemoteRepository remoteRepository, LocalRepository localRepository) {
        INSTANCE = new AudioViewModelFactory(remoteRepository, localRepository);
    }

    public static synchronized AudioViewModelFactory getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("PageViewModelFactory not initialized");
        }
        return INSTANCE;
    }

    private AudioViewModelFactory(RemoteRepository remoteRepository, LocalRepository localRepository) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AudioViewModel.class)) {
            //noinspection unchecked
            return (T) new AudioViewModel(remoteRepository, localRepository);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}
