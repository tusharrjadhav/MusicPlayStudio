package music.com.musicplaystudio.data.source.local;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import music.com.musicplaystudio.data.Audio;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Tushar_temp on 17/12/17
 */
@RunWith(AndroidJUnit4.class)
public class AudioDaoTest {

    private static final Audio AUDIO = new Audio("Song", "Url", "artists", "coverimage", false, false);

    private MusicPlayDatabase mDatabase;

    @Before
    public void setUp() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MusicPlayDatabase.class).build();
    }

    @After
    public void closeDb() {
        mDatabase.close();
    }

    @Test
    public void insertAudioAndGetBySongName() {
        // When inserting a Audio
        mDatabase.audioDao().insertAudio(AUDIO);

        // When getting the Audio by id from the database
        Audio dbAudio = mDatabase.audioDao().getAudioBySong(AUDIO.getSong());

        // The dbAudio data contains the expected values
        assertAudio(dbAudio, AUDIO);
    }

    @Test
    public void insertAudioAndGetByFavarite() {
        AUDIO.setFavorite(true);
        // When inserting a Audio
        mDatabase.audioDao().insertAudio(AUDIO);

        // When getting the Audio by id from the database
        List<Audio> dbAudio = mDatabase.audioDao().getFavoriteAudioList(true);
        assertThat(dbAudio.isEmpty(), is(false));
        // The dbAudio data contains the expected values
        assertAudio(dbAudio.get(0), AUDIO);
    }

    @Test
    public void insertAudioAndGetByHistory() {
        AUDIO.setPlayed(true);
        // When inserting a Audio
        mDatabase.audioDao().insertAudio(AUDIO);

        // When getting the Audio by id from the database
        List<Audio> dbAudio = mDatabase.audioDao().getAudioPlayedHistory(true);
        assertThat(dbAudio.isEmpty(), is(false));
        // The dbAudio data contains the expected values
        assertAudio(dbAudio.get(0), AUDIO);
    }

    private void assertAudio(Audio dbAudio, Audio tempAudio) {
        assertThat(dbAudio, notNullValue());
        assertThat(dbAudio.getSong(), is(tempAudio.getSong()));
        assertThat(dbAudio.getArtists(), is(tempAudio.getArtists()));
        assertThat(dbAudio.getCover_image(), is(tempAudio.getCover_image()));
        assertThat(dbAudio.getUrl(), is(tempAudio.getUrl()));
        assertThat(dbAudio.isFavorite(), is(tempAudio.isFavorite()));
        assertThat(dbAudio.isPlayed(), is(tempAudio.isPlayed()));

    }

}