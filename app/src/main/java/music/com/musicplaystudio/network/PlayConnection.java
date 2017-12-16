package music.com.musicplaystudio.network;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import music.com.musicplaystudio.data.Audio;

/**
 * Created by Tushar_temp on 16/12/17
 */

public class PlayConnection {
    private static final String TAG = PlayConnection.class.getSimpleName();

    /**
     * End point URL for Audio list
     */
    private static final String MAIN_URL = "http://starlord.hackerearth.com/studio";

    /**
     * Open URL connection and read data
     *
     * @param urlSpec to fetch data
     * @return url data in byte[]
     * @throws IOException throwing exception while Network connection
     */
    private byte[] getUrlBytes(String urlSpec) throws IOException {
        Log.e(TAG, "Download from URL: " + urlSpec);

        URL url = new URL(urlSpec);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        byte[] outBytes = null;

        try {
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int bytesRead;
                byte[] buffer = new byte[1024];

                // Call read repeatedly until the connection runs out of data
                // InputStream will yield bytes as they are available
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
                out.close();
                outBytes = out.toByteArray();
            }
        } catch (SocketTimeoutException ste) {
            Log.e(TAG, "Socket timeout: ", ste);
        } catch (UnknownServiceException use) {
            Log.e(TAG, "Unknown service exception: ", use);
        } catch (IOException ioe) {
            Log.e(TAG, "IO Exception: ", ioe);
        } finally {
            connection.disconnect();
        }

        return outBytes;
    }

    /**
     * Building url for fetching all ids in Json format
     *
     * @return list of AudioItem
     */
    public ArrayList<Audio> fetchPhotoItems() {
        String url = Uri.parse(MAIN_URL).buildUpon()
                .build().toString();
        return downloadAudiosData(url);
    }

    /**
     * Fetch data from url in Json format and parse it using Gson
     *
     * @param url server url for fetching data
     * @return list of AudioItem
     */
    private ArrayList<Audio> downloadAudiosData(String url) {
        ArrayList<Audio> items = new ArrayList<>();

        try {
            String jsonString = new String(getUrlBytes(url));
            Log.i(TAG, "Audio Json: " + jsonString);

            Gson gson = new GsonBuilder().create();
            ArrayList<Audio> audioFeed = gson.fromJson(jsonString, ArrayList.class);
            items.addAll(audioFeed);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch AudioData", ioe);
        }
        return items;
    }
}
