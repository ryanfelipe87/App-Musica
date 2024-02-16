package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.AlbumDto;
import com.bitzen.appmusica.dtos.ArtistDto;
import com.bitzen.appmusica.services.ArtistService;
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

class ArtistControllerTest {

    private static final Long ID = 1L;
    private static final String NAME = "Chester";
    private static final String IMAGE_URL = "http://chester.com";
    public static final int INDEX = 0;

    private ArtistDto artistDto = new ArtistDto();

    @InjectMocks
    private ArtistController artistController;

    @Mock
    private ArtistService artistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startArtist();
    }

    @Test
    void whenCreateArtistReturnSuccess() {
        when(artistService.createArtist(any())).thenReturn(artistDto);

        ResponseEntity<ArtistDto> response = artistController.create(artistDto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void whenListAllArtistsReturnSuccess() {
        List<ArtistDto> artistDtoList = new ArrayList<>();
        artistDto.setId(ID);
        artistDto.setName(NAME);
        artistDto.setImageUrl(IMAGE_URL);
        artistDtoList.add(artistDto);

        ArtistDto artistDto1 = new ArtistDto();
        artistDto1.setId(2L);
        artistDto1.setName("Shirley");
        artistDto1.setImageUrl("gospel.com");
        artistDtoList.add(artistDto1);

        when(artistService.listAll()).thenReturn(artistDtoList);

        List<ArtistDto> response = artistController.listAllArtists();

        assertEquals(artistDtoList.size(), response.size());
        assertEquals(artistDtoList.get(INDEX).getId(), response.get(INDEX).getId());
        assertEquals(artistDtoList.get(INDEX).getName(), response.get(INDEX).getName());
        assertEquals(artistDtoList.get(INDEX).getImageUrl(), response.get(INDEX).getImageUrl());
    }

    @Test
    void whenGetArtistByIdReturnSuccess() {
        artistDto.setId(ID);
        artistDto.setName(NAME);
        artistDto.setImageUrl(IMAGE_URL);

        when(artistService.findArtistById(ID)).thenReturn(artistDto);

        ArtistDto response = artistController.getArtistById(ID);

        assertNotNull(response);

        assertEquals(artistDto.getId(), response.getId());
        assertEquals(artistDto.getName(), response.getName());
        assertEquals(artistDto.getImageUrl(), response.getImageUrl());
    }

    @Test
    void whenUpdateArtistReturnSuccess() {
        artistDto.setId(ID);
        artistDto.setName(NAME);
        artistDto.setImageUrl(IMAGE_URL);

        ArtistDto artistDto1 = new ArtistDto();
        artistDto1.setId(ID);
        artistDto1.setName("Chester Bennington");
        artistDto1.setImageUrl("http://example.com/linkinpark/chester");

        when(artistService.updateArtist(eq(ID), any(ArtistDto.class))).thenReturn(artistDto1);

        ArtistDto response = artistController.updateArtist(ID, artistDto);

        assertNotNull(response);

        assertEquals(artistDto1.getId(), response.getId());
        assertEquals(artistDto1.getName(), response.getName());
        assertEquals(artistDto1.getImageUrl(), response.getImageUrl());
    }

    @Test
    void whenDeleteArtistReturnSuccess() {
        artistController.deleteArtist(ID);
        verify(artistService).deleteArtist(ID);
    }

    private void startArtist(){
        artistDto = new ArtistDto(ID, NAME, IMAGE_URL);
    }
}