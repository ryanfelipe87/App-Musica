package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.ArtistDto;
import com.bitzen.appmusica.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/artist")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    @PostMapping
    public ArtistDto create(@RequestBody ArtistDto artistDto){
        return artistService.createArtist(artistDto);
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
