package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.AlbumDto;
import com.bitzen.appmusica.services.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Create a new album",
            description = "Controller for Album"
    )
    public ResponseEntity<AlbumDto> createAlbum(@RequestBody AlbumDto albumDto) {
        albumService.createAlbum(albumDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Operation(
            summary = "List all albums",
            description = "Controller for Album"
    )
    public List<AlbumDto> getAllAlbums() {
        return albumService.listAllAlbums();
    }

    @GetMapping(path = "/{id}")
    @Operation(
            summary = "List album by ID",
            description = "Controller for Album"
    )
    public AlbumDto getAlbumById(@PathVariable Long id) {
        return albumService.findAlbumById(id);
    }

    @PutMapping(path = "/{id}")
    @Operation(
            summary = "Update a album",
            description = "Controller for Album"
    )
    public AlbumDto updateAlbum(@PathVariable Long id, @RequestBody AlbumDto albumDto) {
        return albumService.updateAlbum(id, albumDto);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(
            summary = "Delete a album",
            description = "Controller for Album"
    )
    public void deleteAlbumById(@PathVariable Long id) {
        albumService.deleteAlbum(id);
    }
}
