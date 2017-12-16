package music.com.musicplaystudio.data.source.local;

import java.util.concurrent.Executor;

import music.com.musicplaystudio.callback.GetAudioListCallBack;
import music.com.musicplaystudio.data.Audio;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class LocalRepository {
    private AudioDao audioDao;
    private Executor executor;

    public LocalRepository(AudioDao couponDAO, Executor executor) {
        this.audioDao = couponDAO;
        this.executor = executor;
    }

    public void getAudioList(final GetAudioListCallBack callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                callback.onAudioLoaded(audioDao.getAudioList());
            }
        };
        executor.execute(runnable);
    }

    public void insertAudio(final Audio body) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                audioDao.insertAudio(body);
            }
        };
        executor.execute(runnable);
    }


}
