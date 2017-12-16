package music.com.musicplaystudio.network;

import java.util.List;

import music.com.musicplaystudio.data.Audio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Tushar_temp on 16/12/17
 */

public interface APIInterface {

    @GET
    Call<List<Audio>> doGetAudioList(@Url String url);

}
