package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.ArtistDto;
import com.bitzen.appmusica.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @PostMapping
    public ResponseEntity<ArtistDto> create(@RequestBody ArtistDto artistDto){
        artistService.createArtist(artistDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<ArtistDto> listAllArtists(){
        return artistService.listAll();
    }

    @GetMapping(path = "/{id}")
    public ArtistDto getArtistById(@PathVariable Long id){
        return artistService.findArtistById(id);
    }

    @PutMapping(path ="/{id}")
    public ArtistDto updateArtist(@PathVariable Long id, @RequestBody ArtistDto artistDto){
        return artistService.updateArtist(id, artistDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteArtist(@PathVariable Long id){
        artistService.deleteArtist(id);
    }
}
