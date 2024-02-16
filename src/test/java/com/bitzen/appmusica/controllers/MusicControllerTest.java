package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.MusicDto;
import com.bitzen.appmusica.services.MusicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MusicControllerTest {

    private static final Long ID = 1L;
    private static final String TITLE = "What i've done";
    private static final String DURATION = "3:25";
    private static final Integer TRACK = 1;
    private static final int INDEX = 0;

    private MusicDto musicDto = new MusicDto();

    @InjectMocks
    private MusicController musicController;

    @Mock
    private MusicService musicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startMusic();
    }

    @Test
    void whenCreateMusicReturnSuccess() {
        when(musicService.createMusic(any())).thenReturn(musicDto);

        ResponseEntity<MusicDto> response = musicController.createMusic(musicDto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void whenGetAllMusicsReturnSuccess() {
        List<MusicDto> musicDtoList = new ArrayList<>();
        musicDto.setId(ID);
        musicDto.setTitle(TITLE);
        musicDto.setDuration(DURATION);
        musicDto.setTrack(TRACK);
        musicDtoList.add(musicDto);

        MusicDto musicDto1 = new MusicDto();
        musicDto1.setId(2L);
        musicDto1.setTitle("Faint");
        musicDto1.setDuration("4:11");
        musicDto1.setTrack(2);
        musicDtoList.add(musicDto1);

        when(musicService.listAllMusics()).thenReturn(musicDtoList);

        List<MusicDto> response = musicController.getAllMusics();

        assertEquals(musicDtoList.size(), response.size());
        assertEquals(musicDtoList.get(INDEX).getId(), response.get(INDEX).getId());
        assertEquals(musicDtoList.get(INDEX).getTitle(), response.get(INDEX).getTitle());
        assertEquals(musicDtoList.get(INDEX).getDuration(), response.get(INDEX).getDuration());
        assertEquals(musicDtoList.get(INDEX).getTrack(), response.get(INDEX).getTrack());
    }

    @Test
    void whenGetMusicByIdReturnSuccess() {
        musicDto.setId(ID);
        musicDto.setTitle(TITLE);
        musicDto.setDuration(DURATION);
        musicDto.setTrack(TRACK);

        when(musicService.findMusicById(ID)).thenReturn(musicDto);

        MusicDto response = musicController.getMusicById(ID);

        assertNotNull(response);

        assertEquals(musicDto.getId(), response.getId());
        assertEquals(musicDto.getTitle(), response.getTitle());
        assertEquals(musicDto.getDuration(), response.getDuration());
        assertEquals(musicDto.getTrack(), response.getTrack());
    }

    @Test
    void whenUpdateMusicReturnSuccess() {
        musicDto.setId(ID);
        musicDto.setTitle(TITLE);
        musicDto.setDuration(DURATION);
        musicDto.setTrack(TRACK);

        MusicDto musicDto1 = new MusicDto();
        musicDto1.setId(ID);
        musicDto1.setTitle("What i've done Live");
        musicDto1.setDuration("5:01");
        musicDto1.setTrack(3);

        when(musicService.updateMusic(eq(ID), any(MusicDto.class))).thenReturn(musicDto1);

        MusicDto response = musicController.updateMusic(ID, musicDto);

        assertNotNull(response);

        assertEquals(musicDto1.getId(), response.getId());
        assertEquals(musicDto1.getTitle(), response.getTitle());
        assertEquals(musicDto1.getDuration(), response.getDuration());
        assertEquals(musicDto1.getTrack(), response.getTrack());
    }

    @Test
    void whenDeleteMusicByIdReturnSuccess() {
        musicController.deleteMusicById(ID);
        verify(musicService).deleteMusic(ID);
    }

    private void startMusic(){
        musicDto = new MusicDto(ID, TITLE, DURATION, TRACK);
    }
}