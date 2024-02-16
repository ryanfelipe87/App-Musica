package com.bitzen.appmusica.controllers;

import com.bitzen.appmusica.dtos.MusicDto;
import com.bitzen.appmusica.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/musics")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @PostMapping
    public ResponseEntity<MusicDto> createMusic(@RequestBody MusicDto musicDto){
        musicService.createMusic(musicDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<MusicDto> getAllMusics(){
        return musicService.listAllMusics();
    }

    @GetMapping(path = "/{id}")
    public MusicDto getMusicById(@PathVariable Long id){
        return musicService.findMusicById(id);
    }

    @PutMapping(path = "/{id}")
    public MusicDto updateMusic(@PathVariable Long id, @RequestBody MusicDto musicDto){
        return musicService.updateMusic(id, musicDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteMusicById(@PathVariable Long id){
        musicService.deleteMusic(id);
    }
}
