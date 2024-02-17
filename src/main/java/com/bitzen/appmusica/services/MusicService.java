package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.MusicAlbumDTO;
import com.bitzen.appmusica.dtos.MusicDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.exceptions.NotFoundException;
import com.bitzen.appmusica.models.Album;
import com.bitzen.appmusica.models.Music;
import com.bitzen.appmusica.patterns.LoggerSingleton;
import com.bitzen.appmusica.repositories.MusicRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public MusicDto createMusic(MusicDto musicDto) {
        logger.info("Creating music...");
        validateDuration(musicDto.getDuration());
        validateNotBlankAndNotNull(musicDto.getTitle(), musicDto.getDuration(), musicDto.getTrack());
        Music music = convertToEntity(musicDto);
        music = repository.save(music);
        logger.info("Music created successfully!");
        return convertToDto(music);
    }

    public List<MusicDto> listAllMusics() {
        logger.info("Listing all musics...");
        List<Music> musics = repository.findAll();
        logger.info("Complete listing!");
        return musics.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MusicDto findMusicById(Long id) {
        Music music = repository.findById(id).orElse(null);
        logger.info("Finding music by ID: " + id);
        if (music != null) {
            return convertToDto(music);
        }
        throw new BadRequestException("Invalid request with this ID: " + id);
    }

    public MusicDto updateMusic(Long id, MusicDto musicDto) {
        logger.info("Updating music by ID...");
        validateDuration(musicDto.getDuration());
        Optional<Music> musicOptional = repository.findById(id);
        if (musicOptional.isPresent()) {
            Music music = convertToEntity(musicDto);
            music = repository.save(music);
            logger.info("Music updated!");
            return convertToDto(music);
        }
        throw new NotFoundException("Request not found");
    }

    public void deleteMusic(Long id) {
        logger.info("Removing...");
        repository.deleteById(id);
    }

    public List<MusicAlbumDTO> findMusicAndAlbumByArtistId(Long artistId) {
        return repository.findMusicAndAlbumByArtistId(artistId);
    }

    private MusicDto convertToDto(Music music) {
        MusicDto musicDto = new MusicDto();
        BeanUtils.copyProperties(music, musicDto);
        return musicDto;
    }

    private Music convertToEntity(MusicDto musicDto) {
        validateTrack(musicDto.getTrack());
        Music music = new Music();
        BeanUtils.copyProperties(musicDto, music);
        Album album = new Album();
        album.setId(musicDto.getAlbumId());
        music.setAlbum(album);
        return music;
    }

    private void validateTrack(Integer track) {
        if (track <= 0)
            throw new BadRequestException("Track numbers must be greater than zero");
    }

    public void validateDuration(String duration) {
        String[] parts = duration.split(":");

        if (parts.length != 2) {
            throw new BadRequestException("A duração deve estar no formato mm:ss");
        }

        int minutes = Integer.parseInt(parts[0]);
        if (minutes < 0 || minutes > 59) {
            throw new BadRequestException("Os minutos devem estar entre 0 e 59");
        }

        int seconds = Integer.parseInt(parts[1]);
        if (seconds < 0 || seconds > 59) {
            throw new BadRequestException("Os segundos devem estar entre 0 e 59");
        }
    }

    private void validateNotBlankAndNotNull(String title, String duration, Integer track) {
        if (!StringUtils.hasText(title) || !StringUtils.hasText(duration) || !StringUtils.hasText(String.valueOf(track)))
            throw new BadRequestException("Cannot be null or empty");
    }
}
