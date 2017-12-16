package music.com.musicplaystudio.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Tushar_temp on 16/12/17
 */

@Entity(tableName = "audio")
public class Audio implements Serializable {

    @PrimaryKey
    @NonNull
    private String song = "";
    private String url;
    private String artists;
    private String cover_image;

    private boolean favorite = false;
    private boolean played = false;

    public Audio(@NonNull String song, String url, String artists, String cover_image, boolean favorite, boolean played) {
        this.song = song;
        this.url = url;
        this.artists = artists;
        this.cover_image = cover_image;
        this.favorite = favorite;
        this.played = played;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }
}
