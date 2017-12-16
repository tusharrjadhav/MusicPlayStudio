package music.com.musicplaystudio.viewmodel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import music.com.musicplaystudio.callback.GetAudioListCallBack;
import music.com.musicplaystudio.data.Audio;
import music.com.musicplaystudio.data.source.local.LocalRepository;
import music.com.musicplaystudio.data.source.remote.RemoteRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Tushar_temp on 17/12/17
 */
public class AudioViewModelTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private LocalRepository mockLocalRepository;

    @Mock
    private RemoteRepository mockRemoteRepository;

    private AudioViewModel viewModel;

    private ArgumentCaptor<GetAudioListCallBack> argumentCaptor = ArgumentCaptor.forClass(GetAudioListCallBack.class);

    @Before
    public void setUp() throws Exception {
        viewModel = new AudioViewModel(mockRemoteRepository, mockLocalRepository);
    }

    @Test
    public void testGetAllAudioListLiveData() throws Exception {
        assertThat(viewModel.getAllAudioListLiveData()).isNotNull();
    }

    @Test
    public void testUpdateAudioData() throws Exception {
        Audio mockAudio = mock(Audio.class);
        viewModel.updateAudioData(mockAudio);
        verify(mockLocalRepository).addAudio(eq(mockAudio));
    }

    @Test
    public void testGetAudioListFromService() throws Exception {
        viewModel.getAudioListFromService();
        verify(mockRemoteRepository).getAudioList(any(GetAudioListCallBack.class));
    }

    @Test
    public void testGetHistoryAudiodList() throws Exception {
        viewModel.getHistoryAudiodList();
        verify(mockLocalRepository).getAudioHistoryList(any(GetAudioListCallBack.class));
    }

    @Test
    public void testGetFavoriteAudiodList() throws Exception {
        viewModel.getFavoriteAudiodList();
        verify(mockLocalRepository).getFavoriteAudioList(any(GetAudioListCallBack.class));
    }

}