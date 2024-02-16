package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.AlbumDto;
import com.bitzen.appmusica.services.AlbumService;
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

class AlbumControllerTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Meteora";
    private static final String YEAR = "2000";
    private static final String IMAGE_URL = "lp.com";

    public static final int INDEX = 0;

    private AlbumDto albumDto = new AlbumDto();

    @InjectMocks
    private AlbumController albumController;

    @Mock
    private AlbumService albumService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAlbum();
    }

    @Test
    void whenCreateAlbumReturnSuccess() {
        when(albumService.createAlbum(any())).thenReturn(albumDto);

        ResponseEntity<AlbumDto> response = albumController.createAlbum(albumDto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    void whenGetAllAlbumsWithSuccess() {
       List<AlbumDto> albumDtoList = new ArrayList<>();
       albumDto.setId(ID);
       albumDto.setTitle(TITLE);
       albumDto.setYear(YEAR);
       albumDto.setImageUrl(IMAGE_URL);
       albumDtoList.add(albumDto);

       AlbumDto albumDto1 = new AlbumDto();
       albumDto1.setId(2L);
       albumDto1.setTitle("Canção do céu");
       albumDto1.setYear("1996");
       albumDto1.setImageUrl("gospel.com");
       albumDtoList.add(albumDto1);

       when(albumService.listAllAlbums()).thenReturn(albumDtoList);

       List<AlbumDto> response = albumController.getAllAlbums();

       assertEquals(albumDtoList.size(), response.size());
       assertEquals(albumDtoList.get(INDEX).getId(), response.get(INDEX).getId());
       assertEquals(albumDtoList.get(INDEX).getTitle(), response.get(INDEX).getTitle());
       assertEquals(albumDtoList.get(INDEX).getYear(), response.get(INDEX).getYear());
       assertEquals(albumDtoList.get(INDEX).getImageUrl(), response.get(INDEX).getImageUrl());
    }

    @Test
    void whenGetAlbumByIdReturnSuccess() {
        albumDto.setId(ID);
        albumDto.setTitle(TITLE);
        albumDto.setYear(YEAR);
        albumDto.setImageUrl(IMAGE_URL);

        when(albumService.findAlbumById(ID)).thenReturn(albumDto);

        AlbumDto response = albumController.getAlbumById(ID);

        assertNotNull(response);

        assertEquals(albumDto.getId(), response.getId());
        assertEquals(albumDto.getTitle(), response.getTitle());
        assertEquals(albumDto.getYear(), response.getYear());
        assertEquals(albumDto.getImageUrl(), response.getImageUrl());
    }

    @Test
    void whenUpdateAlbumReturnSuccess() {
        albumDto.setId(ID);
        albumDto.setTitle(TITLE);
        albumDto.setYear(YEAR);
        albumDto.setImageUrl(IMAGE_URL);

        AlbumDto albumDto1 = new AlbumDto();
        albumDto1.setId(ID);
        albumDto1.setTitle("Meteora Updated");
        albumDto1.setYear(YEAR);
        albumDto1.setImageUrl("http://example.com/linkinpark");

        when(albumService.updateAlbum(eq(ID), any(AlbumDto.class))).thenReturn(albumDto1);

        AlbumDto response = albumController.updateAlbum(ID, albumDto);

        assertNotNull(response);

        assertEquals(albumDto1.getId(), response.getId());
        assertEquals(albumDto1.getTitle(), response.getTitle());
        assertEquals(albumDto1.getYear(), response.getYear());
        assertEquals(albumDto1.getImageUrl(), response.getImageUrl());
    }

    @Test
    void whenDeleteAlbumByIdReturnSuccess() {
        albumController.deleteAlbumById(ID);
        verify(albumService).deleteAlbum(ID);
    }

    private void startAlbum(){
        albumDto = new AlbumDto(ID, TITLE, YEAR, IMAGE_URL);
    }
}