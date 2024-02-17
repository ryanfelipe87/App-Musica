package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.AlbumDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.exceptions.NotFoundException;
import com.bitzen.appmusica.models.Album;
import com.bitzen.appmusica.repositories.AlbumRepository;
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
class AlbumServiceTest {

    private static final Long ID = 1L;
    private static final String TITLE = "Meteora";
    private static final String YEAR = "2000";
    private static final String IMAGE_URL = "http://linkinpark.com/Meteora";

    @InjectMocks
    private AlbumService albumService;

    @Mock
    private ArtistService artistService;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private Logger logger;

    private AlbumDto albumDto;
    private Optional<Album> optionalAlbum;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAlbum();
    }

//    @Test
//    void whenCreateAlbumThenReturnSuccess() {
//        albumDto.setId(ID);
//        albumDto.setTitle(TITLE);
//        albumDto.setYear(YEAR);
//        albumDto.setImageUrl(IMAGE_URL);
//
//        Album album = new Album();
//        album.setId(ID);
//        album.setTitle(TITLE);
//        album.setYear(YEAR);
//        album.setImageUrl(IMAGE_URL);
//        when(albumRepository.save(any(Album.class))).thenReturn(album);
//
//        AlbumDto response = albumService.createAlbum(albumDto);
//
//        assertNotNull(response);
//        assertEquals(album.getId(), response.getId());
//        assertEquals(album.getTitle(), response.getTitle());
//        assertEquals(album.getYear(), response.getYear());
//        assertEquals(album.getImageUrl(), response.getImageUrl());
//        assertEquals(album.getArtist(), response.getArtistId());
//
//        ArgumentCaptor<Album> albumCaptor = ArgumentCaptor.forClass(Album.class);
//        verify(albumRepository).save(albumCaptor.capture());
//        Album capturedAlbum = albumCaptor.getValue();
//        assertEquals(albumDto.getId(), capturedAlbum.getId());
//        assertEquals(albumDto.getTitle(), capturedAlbum.getTitle());
//        assertEquals(albumDto.getYear(), capturedAlbum.getYear());
//        assertEquals(albumDto.getImageUrl(), capturedAlbum.getImageUrl());
//        assertEquals(albumDto.getArtistId(), capturedAlbum.getArtist());
//
//        verify(logger).info("Creating album...");
//        verify(logger).info("Album created successfully!");
//    }

    @Test
    void whenListAllAlbumsThenReturnSuccess() {
        List<Album> albums = new ArrayList<>();
        albums.add(new Album(ID, TITLE, YEAR, IMAGE_URL));
        albums.add(new Album(2L, "Album 2", "2003", "http://linkinpark.com/TheLivingThings"));

        when(albumRepository.findAll()).thenReturn(albums);

        List<AlbumDto> albumDtoList = albumService.listAllAlbums();

        assertNotNull(albumDtoList);
        assertFalse(albumDtoList.isEmpty());

        assertEquals(albums.size(), albumDtoList.size());
        for (int i = 0; i < albums.size(); i++){
            Album album = albums.get(i);
            albumDto = albumDtoList.get(i);
            assertEquals(album.getId(), albumDto.getId());
            assertEquals(album.getTitle(), albumDto.getTitle());
            assertEquals(album.getYear(), albumDto.getYear());
            assertEquals(album.getImageUrl(), albumDto.getImageUrl());
        }

        verify(logger).info("Listing all albums...");
        verify(logger).info("Complete listing!");
    }

    @Test
    void whenFindAlbumByIdTheReturnSuccess() {
        Album album = new Album();
        when(albumRepository.findById(ID)).thenReturn(Optional.of(album));

        albumDto = albumService.findAlbumById(ID);
        assertNotNull(albumDto);

        assertEquals(album.getId(), albumDto.getId());
        assertEquals(album.getTitle(), albumDto.getTitle());
        assertEquals(album.getYear(), albumDto.getYear());
        assertEquals(album.getImageUrl(), albumDto.getImageUrl());

        verify(logger).info("Finding album by ID: " + ID);
    }

    @Test
    void whenFindAlbumByIdReturnAnBadRequestException(){
        when(albumRepository.findById(anyLong())).thenThrow(new BadRequestException("Invalid request with this ID: "));
        try{
            albumService.findAlbumById(ID);
        }catch (Exception exception){
            assertEquals(BadRequestException.class, exception.getClass());
            assertEquals("Invalid request with this ID: ", exception.getMessage());
        }
    }

    @Test
    void whenUpdateAlbumThenReturnSuccess() {

    }

    @Test
    void whenUpdateAlbumReturnAnNotFoundException(){
        when(albumRepository.findById(anyLong())).thenReturn(Optional.empty());

        try{
            albumService.updateAlbum(ID, albumDto);
            fail("Expected NotFoundException was not thrown");
        }catch (NotFoundException exception){
            assertEquals("Request not found", exception.getMessage());
        }
    }

    @Test
    void whenDeleteAlbumThenReturnSuccess() {
        Album album = new Album();
        when(albumRepository.findById(ID)).thenReturn(Optional.of(album));
        albumService.deleteAlbum(ID);
        verify(albumRepository).deleteById(ID);
        verify(logger).info("Removing...");
    }

    private void startAlbum(){
        albumDto = new AlbumDto(ID, TITLE, YEAR, IMAGE_URL, 1L);
        optionalAlbum = Optional.of(new Album(ID, TITLE, YEAR, IMAGE_URL));
    }
}