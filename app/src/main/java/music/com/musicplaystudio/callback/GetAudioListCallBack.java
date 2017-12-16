package music.com.musicplaystudio.callback;

import java.util.List;

import music.com.musicplaystudio.data.Audio;

/**
 * Created by Tushar_temp on 16/12/17
 */

public interface GetAudioListCallBack {

    void onAudioLoaded(List<Audio> audioList);

    void onDataNotAvailable();
}
