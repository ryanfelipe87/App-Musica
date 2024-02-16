package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.AlbumDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.models.Album;
import com.bitzen.appmusica.patterns.LoggerSingleton;
import com.bitzen.appmusica.repositories.AlbumRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    @Autowired
    private Logger logger = LoggerSingleton.getLogger();

    public AlbumDto createAlbum(AlbumDto albumDto){
        logger.info("Creating album...");
        Album album = convertToEntity(albumDto);
        album = repository.save(album);
        logger.info("Album created successfully!");
        return convertToDto(album);
    }

    public List<AlbumDto> listAllAlbums(){
        logger.info("Listing all albums...");
        List<Album> albums = repository.findAll();
        logger.info("Complete listing!");
        return albums.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public AlbumDto findAlbumById(Long id){
        Album album = repository.findById(id).orElse(null);
        if(album != null){
            return convertToDto(album);
        }
        throw new BadRequestException("Not exists album with this ID: " + id);
    }

    public AlbumDto updateAlbum(Long id, AlbumDto albumDto){
        logger.info("Updating album by ID...");
        Optional<Album> albumOptional = repository.findById(id);
        if(albumOptional.isPresent()){
            Album album = convertToEntity(albumDto);
            album = repository.save(album);
            logger.info("Album updated!");
            return convertToDto(album);
        }
        throw new BadRequestException("Not exists album with this ID: " + id);
    }

    public void deleteAlbum(Long id){
        logger.info("Removing...");
        repository.deleteById(id);
    }

    private AlbumDto convertToDto(Album album){
        AlbumDto albumDto = new AlbumDto();
        BeanUtils.copyProperties(album, albumDto);
        return albumDto;
    }
    private Album convertToEntity(AlbumDto albumDto){
        Album album = new Album();
        BeanUtils.copyProperties(albumDto, album);
        return album;
    }
}
