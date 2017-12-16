package music.com.musicplaystudio.data.source.remote;


import android.util.Log;

import java.util.List;

import music.com.musicplaystudio.callback.GetAudioListCallBack;
import music.com.musicplaystudio.data.Audio;
import music.com.musicplaystudio.network.APIClient;
import music.com.musicplaystudio.network.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class RemoteRepository {

    private static final String TAG = RemoteRepository.class.getSimpleName();
    private APIInterface apiInterface;

    public RemoteRepository(APIInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public void getAudioList(final GetAudioListCallBack callback) {
        apiInterface.doGetAudioList(APIClient.AUDIO_LIST_URL).enqueue(new Callback<List<Audio>>() {
            @Override
            public void onResponse(Call<List<Audio>> call, Response<List<Audio>> response) {
                Log.d(TAG, "getAudioList:" + response);
                if (response.isSuccessful()) {
                    callback.onAudioLoaded(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Audio>> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.toString(), t);
                callback.onDataNotAvailable();
            }
        });
    }

}
