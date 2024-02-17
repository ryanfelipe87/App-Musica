package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.ArtistDto;
import com.bitzen.appmusica.exceptions.BadRequestException;
import com.bitzen.appmusica.exceptions.NotFoundException;
import com.bitzen.appmusica.models.Artist;
import com.bitzen.appmusica.patterns.LoggerSingleton;
import com.bitzen.appmusica.repositories.ArtistRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository repository;

    @Autowired
    private Logger logger = LoggerSingleton.getLogger();

    public ArtistDto createArtist(ArtistDto artistDto) {
        logger.info("Creating artist...");
        Artist artist = convertToEntity(artistDto);
        validateNotBlankAndNotNull(artistDto.getName(), artistDto.getImageUrl());
        artist = repository.save(artist);
        logger.info("Artist created successfully!");
        return convertToDto(artist);
    }

    public List<ArtistDto> listAll() {
        logger.info("Listing all artists...");
        List<Artist> artists = repository.findAll();
        logger.info("Complete listing!");
        return artists.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ArtistDto findArtistById(Long id) {
        logger.info("Finding artist by ID: " + id);
        Artist artist = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Artist not found with ID: " + id));

        return convertToDto(artist);
    }

    public ArtistDto updateArtist(Long id, ArtistDto artistDto) {
        logger.info("Updating artist by ID...");
        Optional<Artist> artistOptional = repository.findById(id);
        if (artistOptional.isPresent()) {
            Artist artist = convertToEntity(artistDto);
            artist = repository.save(artist);
            logger.info("Artist updated!");
            return convertToDto(artist);
        }
        throw new NotFoundException("Request not found");
    }

    public void deleteArtist(Long id) {
        Optional<Artist> artistOptional = repository.findById(id);
        if (artistOptional.isPresent()) {
            repository.deleteById(id);
            logger.info("Removing...");
        }
    }

    private ArtistDto convertToDto(Artist artist) {
        ArtistDto artistDto = new ArtistDto();
        BeanUtils.copyProperties(artist, artistDto);
        return artistDto;
    }

    public Artist convertToEntity(ArtistDto artistDto) {
        Artist artist = new Artist();
        BeanUtils.copyProperties(artistDto, artist);
        return artist;
    }

    private void validateNotBlankAndNotNull(String name, String imageUrl){
        if(!StringUtils.hasText(name) || !StringUtils.hasText(imageUrl))
            throw new BadRequestException("Cannot be null or empty");
    }
}
