package music.com.musicplaystudio;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import music.com.musicplaystudio.data.source.local.LocalRepository;
import music.com.musicplaystudio.data.source.local.MusicPlayDatabase;
import music.com.musicplaystudio.data.source.remote.RemoteRepository;
import music.com.musicplaystudio.network.APIClient;
import music.com.musicplaystudio.network.APIInterface;
import music.com.musicplaystudio.viewmodel.AudioViewModel;
import music.com.musicplaystudio.viewmodel.AudioViewModelFactory;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class PlayApplication extends Application {


    private static final String TAG = PlayApplication.class.getSimpleName();

    private MusicPlayDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = MusicPlayDatabase.getInstance(this);
        RemoteRepository remoteRepository = new RemoteRepository(APIClient.getClient().create(APIInterface.class));
        LocalRepository localRepository = new LocalRepository(database.audioDao(), getExecutor());
        AudioViewModelFactory.initialize(remoteRepository, localRepository);
    }


    public Executor getExecutor(){
        return  Executors.newFixedThreadPool(2);
    }

    public static AudioViewModel obtainViewModel(FragmentActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        AudioViewModelFactory factory = AudioViewModelFactory.getInstance();

        return ViewModelProviders.of(activity, factory).get(AudioViewModel.class);
    }
}
