package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.ArtistDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.exceptions.NotFoundException;
import com.bitzen.appmusica.models.Artist;
import com.bitzen.appmusica.repositories.ArtistRepository;
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
class ArtistServiceTest {

    private static final Long ID = 1L;
    private static final String NAME = "Ryan";
    private static final String IMAGE_URL = "http://sportrecife.com";

    @InjectMocks
    private ArtistService artistService;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private Logger logger;

    private ArtistDto artistDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startArtist();
    }

    @Test
    void whenCreateArtistThenReturnSuccess() {
        artistDto.setId(ID);
        artistDto.setName(NAME);
        artistDto.setImageUrl(IMAGE_URL);

        Artist artist = new Artist();
        artist.setId(ID);
        artist.setName(NAME);
        artist.setImageUrl(IMAGE_URL);
        when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        ArtistDto response = artistService.createArtist(artistDto);

        assertNotNull(response);
        assertEquals(artist.getId(), response.getId());
        assertEquals(artist.getName(), response.getName());
        assertEquals(artist.getImageUrl(), response.getImageUrl());

        ArgumentCaptor<Artist> artistCaptor = ArgumentCaptor.forClass(Artist.class);
        verify(artistRepository).save(artistCaptor.capture());
        Artist capturedArtist = artistCaptor.getValue();
        assertEquals(artistDto.getId(), capturedArtist.getId());
        assertEquals(artistDto.getName(), capturedArtist.getName());
        assertEquals(artistDto.getImageUrl(), capturedArtist.getImageUrl());

        verify(logger).info("Creating artist...");
        verify(logger).info("Artist created successfully!");
    }

    @Test
    void whenListAllArtistsTheReturnSuccess() {
        List<Artist> artists = new ArrayList<>();
        artists.add(new Artist(ID, NAME, IMAGE_URL));
        artists.add(new Artist(2L, "Mike Shinoda", "http://linkinpark.com/Mike"));

        when(artistRepository.findAll()).thenReturn(artists);

        List<ArtistDto> artistDtoList = artistService.listAll();

        assertNotNull(artistDtoList);
        assertFalse(artistDtoList.isEmpty());

        assertEquals(artists.size(), artistDtoList.size());
        for (int i = 0; i < artists.size(); i++){
            Artist artist = artists.get(i);
            artistDto = artistDtoList.get(i);
            assertEquals(artist.getId(), artistDto.getId());
            assertEquals(artist.getName(), artistDto.getName());
            assertEquals(artist.getImageUrl(), artistDto.getImageUrl());
        }

        verify(logger).info("Listing all artists...");
        verify(logger).info("Complete listing!");
    }

    @Test
    void whenFindArtistByIdThenReturnSuccess() {
        Artist artist = new Artist();
        when(artistRepository.findById(ID)).thenReturn(Optional.of(artist));

        artistDto = artistService.findArtistById(ID);
        assertNotNull(artistDto);

        assertEquals(artist.getId(), artistDto.getId());
        assertEquals(artist.getName(), artistDto.getName());
        assertEquals(artist.getImageUrl(), artistDto.getImageUrl());

        verify(logger).info("Finding artist by ID: " + ID);
    }

    @Test
    void whenFindArtistByIdReturnAnBadRequestException(){
        when(artistRepository.findById(anyLong())).thenThrow(new BadRequestException("Invalid request with this ID: "));
        try{
            artistService.findArtistById(ID);
        }catch (Exception exception){
            assertEquals(BadRequestException.class, exception.getClass());
            assertEquals("Invalid request with this ID: ", exception.getMessage());
        }
    }

    @Test
    void whenUpdateArtistThenReturnSuccess() {
    }

    @Test
    void whenUpdateArtistReturnAnNotFoundException(){
        when(artistRepository.findById(anyLong())).thenReturn(Optional.empty());

        try{
            artistService.updateArtist(ID, artistDto);
            fail("Expected NotFoundException was not thrown");
        }catch (NotFoundException exception){
            assertEquals("Request not found", exception.getMessage());
        }
    }

    @Test
    void whenDeleteArtistThenReturnSuccess() {
        Artist artist = new Artist();
        when(artistRepository.findById(ID)).thenReturn(Optional.of(artist));
        artistService.deleteArtist(ID);
        verify(artistRepository).deleteById(ID);
        verify(logger).info("Removing...");
    }

    private void startArtist(){
        artistDto = new ArtistDto(ID, NAME, IMAGE_URL);
    }
}