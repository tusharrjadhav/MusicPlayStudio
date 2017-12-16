package music.com.musicplaystudio.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import music.com.musicplaystudio.data.Audio;

/**
 * Created by Tushar_temp on 16/12/17
 */

@Database(entities = {Audio.class}, version = 1)
public abstract class MusicPlayDatabase extends RoomDatabase {
    private static MusicPlayDatabase instance;

    public abstract AudioDao audioDao();

    private static final Object sLock = new Object();

    public static MusicPlayDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        MusicPlayDatabase.class, "MusicPlay.db")
                        .build();
            }
            return instance;
        }
    }
}
