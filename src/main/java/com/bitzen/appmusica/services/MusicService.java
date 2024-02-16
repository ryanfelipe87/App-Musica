package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.MusicDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.models.Music;
import com.bitzen.appmusica.patterns.LoggerSingleton;
import com.bitzen.appmusica.repositories.MusicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MusicService {

    @Autowired
    private MusicRepository repository;

    @Autowired
    private Logger logger = LoggerSingleton.getLogger();

    public MusicDto createMusic(MusicDto musicDto){
        logger.info("Creating music...");
        Music music = convertToEntity(musicDto);
        music = repository.save(music);
        logger.info("Music created successfully!");
        return convertToDto(music);
    }

    public List<MusicDto> listAllMusics(){
        logger.info("Listing all musics...");
        List<Music> musics = repository.findAll();
        logger.info("Complete listing!");
        return musics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MusicDto findMusicById(Long id){
        Music music = repository.findById(id).orElse(null);
        if(music != null){
            return convertToDto(music);
        }
        throw new BadRequestException("Not exists music with this ID: " + id);
    }

    public MusicDto updateMusic(Long id, MusicDto musicDto){
        logger.info("Updating music by ID...");
        Optional<Music> musicOptional = repository.findById(id);
        if(musicOptional.isPresent()){
            Music music = convertToEntity(musicDto);
            music = repository.save(music);
            logger.info("Music updated!");
            return convertToDto(music);
        }
        throw new BadRequestException("Not exists music with this ID: " + id);
    }

    public void deleteMusic(Long id){
        logger.info("Removing...");
        repository.deleteById(id);
    }

    private MusicDto convertToDto(Music music){
        MusicDto musicDto = new MusicDto();
        BeanUtils.copyProperties(music, musicDto);
        return musicDto;
    }

    private Music convertToEntity(MusicDto musicDto){
        validateTrack(musicDto.getTrack());
        Music music = new Music();
        BeanUtils.copyProperties(musicDto, music);
        return music;
    }

    private void validateTrack(Integer track){
        if(track <= 0)
            throw new BadRequestException("Track numbers must be greater than zero");
    }
}
