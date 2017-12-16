package music.com.musicplaystudio.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import music.com.musicplaystudio.data.Audio;

/**
 * Created by Tushar_temp on 16/12/17
 */

@Dao
public interface AudioDao {

    @Query("SELECT * FROM Audio WHERE song = :song")
    Audio getAudioBySong(String song);

    @Query("SELECT * FROM Audio WHERE played = :played")
    List<Audio> getAudioPlayedHistory(boolean played);

    @Query("SELECT * FROM Audio WHERE favorite = :favorite")
    List<Audio> getFavoriteAudioList(boolean favorite);

    @Query("SELECT * FROM Audio")
    List<Audio> getAudioList();

    @Update
    int updateAudio(Audio audio);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAudio(Audio audio);
}
