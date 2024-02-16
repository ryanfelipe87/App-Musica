package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.MusicDto;
import com.bitzen.appmusica.models.Music;
import com.bitzen.appmusica.repositories.MusicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicService {

    @Autowired
    private MusicRepository repository;

    public MusicDto createMusic(MusicDto musicDto){
        Music music = convertToEntity(musicDto);
        music = repository.save(music);
        return convertToDto(music);
    }

    public List<MusicDto> listAllMusics(){
        List<Music> musics = repository.findAll();
        return musics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MusicDto findMusicById(Long id){
        Music music = repository.findById(id).orElse(null);
        if(music != null){
            return convertToDto(music);
        }
        throw new IllegalArgumentException("Not exists music with this ID: " + id);
    }

    public MusicDto updateMusic(Long id, MusicDto musicDto){
        Optional<Music> musicOptional = repository.findById(id);
        if(musicOptional.isPresent()){
            Music music = convertToEntity(musicDto);
            music = repository.save(music);
            return convertToDto(music);
        }
        throw new IllegalArgumentException("Not exists music with this ID: " + id);
    }

    public void deleteMusic(Long id){
        repository.deleteById(id);
    }

    private MusicDto convertToDto(Music music){
        MusicDto musicDto = new MusicDto();
        BeanUtils.copyProperties(music, musicDto);
        return musicDto;
    }

    private Music convertToEntity(MusicDto musicDto){
        Music music = new Music();
        BeanUtils.copyProperties(musicDto, music);
        return music;
    }
}
