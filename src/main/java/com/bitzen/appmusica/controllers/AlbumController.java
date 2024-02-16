package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.AlbumDto;
import com.bitzen.appmusica.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @PostMapping
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody AlbumDto albumDto) {
        albumService.createAlbum(albumDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<AlbumDto> getAllAlbums() {
        return albumService.listAllAlbums();
    }

    @GetMapping(path = "/{id}")
    public AlbumDto getAlbumById(@PathVariable Long id) {
        return albumService.findAlbumById(id);
    }

    @PutMapping(path = "/{id}")
    public AlbumDto updateAlbum(@PathVariable Long id, @RequestBody AlbumDto albumDto) {
        return albumService.updateAlbum(id, albumDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAlbumById(@PathVariable Long id) {
        albumService.deleteAlbum(id);
    }
}
