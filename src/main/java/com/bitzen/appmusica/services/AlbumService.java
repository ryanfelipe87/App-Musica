package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.AlbumDto;
import com.bitzen.appmusica.dtos.ArtistDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.exceptions.NotFoundException;
import com.bitzen.appmusica.models.Album;
import com.bitzen.appmusica.models.Artist;
import com.bitzen.appmusica.patterns.LoggerSingleton;
import com.bitzen.appmusica.repositories.AlbumRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private Logger logger = LoggerSingleton.getLogger();

    public AlbumDto createAlbum(AlbumDto albumDto){
        logger.info("Creating album...");
        Album album = convertToEntity(albumDto);
        validateNotBlankAndNotNull(albumDto.getTitle(), albumDto.getYear(), albumDto.getImageUrl());
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
        logger.info("Finding album by ID: " + id);
        if(album != null){
            return convertToDto(album);
        }
        throw new BadRequestException("Invalid request with this ID: " + id);
    }

    public AlbumDto updateAlbum(Long id, AlbumDto albumDto){
        logger.info("Updating album by ID: " + id);
        Optional<Album> albumOptional = repository.findById(id);
        validateArtistIdOnUpdate(albumDto);
        if(albumOptional.isPresent()){
            Album album = convertToEntity(albumDto);
            album = repository.save(album);
            logger.info("Album updated!");
            return convertToDto(album);
        }
        throw new NotFoundException("Request not found");
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
        album.setArtist(verifyArtist(albumDto.getArtistId()));
        return album;
    }

    private Artist verifyArtist(Long artistId){
        ArtistDto artistById = artistService.findArtistById(artistId);
        if(artistById == null){
            throw new NotFoundException("Artist not found with ID: " + artistId);
        }
        return artistService.convertToEntity(artistById);
    }

    private void validateNotBlankAndNotNull(String title, String year, String imageUrl){
        if(!StringUtils.hasText(title) || !StringUtils.hasText(year) || !StringUtils.hasText(imageUrl))
            throw new BadRequestException("Cannot be null or empty");
    }

    private void validateArtistIdOnUpdate(AlbumDto albumDto){
        if(albumDto.getArtistId() != null)
            albumDto.getArtistId();
    }
}
