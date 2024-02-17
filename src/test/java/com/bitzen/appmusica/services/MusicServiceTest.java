package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.MusicDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.exceptions.NotFoundException;
import com.bitzen.appmusica.models.Music;
import com.bitzen.appmusica.repositories.MusicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class MusicServiceTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Numb";
    private static final String DURATION = "4:00";
    private static final Integer TRACK = 1;

    @InjectMocks
    private MusicService musicService;

    @Mock
    private MusicRepository musicRepository;

    @Mock
    private Logger logger;

    private MusicDto musicDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startMusic();
    }

    @Test
    void whenCreateMusicTheReturnSuccess() {
        musicDto.setId(ID);
        musicDto.setTitle(TITLE);
        musicDto.setDuration(DURATION);
        musicDto.setTrack(TRACK);

        Music music = new Music();
        music.setId(ID);
        music.setTitle(TITLE);
        music.setDuration(DURATION);
        music.setTrack(TRACK);
        when(musicRepository.save(any(Music.class))).thenReturn(music);

        MusicDto response = musicService.createMusic(musicDto);

        assertNotNull(response);
        assertEquals(music.getId(), response.getId());
        assertEquals(music.getTitle(), response.getTitle());
        assertEquals(music.getDuration(), response.getDuration());
        assertEquals(music.getTrack(), response.getTrack());

        ArgumentCaptor<Music> musicCaptor = ArgumentCaptor.forClass(Music.class);
        verify(musicRepository).save(musicCaptor.capture());
        Music capturedMusic = musicCaptor.getValue();
        assertEquals(musicDto.getId(), capturedMusic.getId());
        assertEquals(musicDto.getTitle(), capturedMusic.getTitle());
        assertEquals(musicDto.getDuration(), capturedMusic.getDuration());
        assertEquals(musicDto.getTrack(), capturedMusic.getTrack());

        verify(logger).info("Creating music...");
        verify(logger).info("Music created successfully!");
    }

    @Test
    void whenListAllMusicsTheReturnSuccess() {
        List<Music> musics = new ArrayList<>();
        musics.add(new Music(ID, TITLE, DURATION, TRACK));
        musics.add(new Music(2L, "The catalyst", "6:02", 9));

        when(musicRepository.findAll()).thenReturn(musics);

        List<MusicDto> musicDtoList = musicService.listAllMusics();

        assertNotNull(musicDtoList);
        assertFalse(musicDtoList.isEmpty());

        assertEquals(musics.size(), musicDtoList.size());
        for (int i = 0; i < musics.size(); i++){
            Music music = musics.get(i);
            musicDto = musicDtoList.get(i);
            assertEquals(music.getId(), musicDto.getId());
            assertEquals(music.getTitle(), musicDto.getTitle());
            assertEquals(music.getDuration(), musicDto.getDuration());
            assertEquals(music.getTrack(), musicDto.getTrack());
        }

        verify(logger).info("Listing all musics...");
        verify(logger).info("Complete listing!");
    }

    @Test
    void whenFindMusicByIdThenReturnSuccess() {
        Music music = new Music();
        when(musicRepository.findById(ID)).thenReturn(Optional.of(music));

        musicDto = musicService.findMusicById(ID);
        assertNotNull(musicDto);

        assertEquals(music.getId(), musicDto.getId());
        assertEquals(music.getTitle(), musicDto.getTitle());
        assertEquals(music.getDuration(), musicDto.getDuration());
        assertEquals(music.getTrack(), musicDto.getTrack());

        verify(logger).info("Finding music by ID: " + ID);
    }

    @Test
    void whenFindMusicByIdReturnAnBadRequestException(){
        when(musicRepository.findById(anyLong())).thenThrow(new BadRequestException("Invalid request with this ID: "));
        try{
            musicService.findMusicById(ID);
        }catch (Exception exception){
            assertEquals(BadRequestException.class, exception.getClass());
            assertEquals("Invalid request with this ID: ", exception.getMessage());
        }
    }

    @Test
    void whenUpdateMusicThenReturnSuccess() {
    }

    @Test
    void whenUpdateMusicThenReturnAnNotFoundException(){
        when(musicRepository.findById(anyLong())).thenReturn(Optional.empty());

        try{
            musicService.updateMusic(ID, musicDto);
            fail("Expected NotFoundException was not thrown");
        }catch (NotFoundException exception){
            assertEquals("Request not found", exception.getMessage());
        }
    }

    @Test
    void whenDeleteMusicThenReturnSuccess() {
        Music music = new Music();
        when(musicRepository.findById(ID)).thenReturn(Optional.of(music));
        musicService.deleteMusic(ID);
        verify(musicRepository).deleteById(ID);
        verify(logger).info("Removing...");
    }

    private void startMusic(){
        musicDto = new MusicDto(ID, TITLE, DURATION, TRACK);
    }
}