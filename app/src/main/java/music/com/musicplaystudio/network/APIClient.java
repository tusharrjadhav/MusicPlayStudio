package music.com.musicplaystudio.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class APIClient {

    public static final String AUDIO_LIST_URL = "http://starlord.hackerearth.com/studio";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        return retrofit;
    }

}
