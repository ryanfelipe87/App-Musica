package com.bitzen.appmusica.services;

import com.bitzen.appmusica.dtos.ArtistDto;
import com.bitzen.appmusica.models.Artist;
import com.bitzen.appmusica.repositories.ArtistRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository repository;

    public ArtistDto createArtist(ArtistDto artistDto) {
        Artist artist = convertToEntity(artistDto);
        artist = repository.save(artist);
        return convertToDto(artist);
    }

    public List<ArtistDto> listAll() {
        List<Artist> artists = repository.findAll();
        return artists.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ArtistDto findArtistById(Long id) {
        Artist artist = repository.findById(id).orElse(null);
        if (artist != null) {
            return convertToDto(artist);
        }
        throw new IllegalArgumentException("Not exists artist with this ID: " + id);
    }

    public ArtistDto updateArtist(Long id, ArtistDto artistDto) {
        Optional<Artist> artistOptional = repository.findById(id);
        if (artistOptional.isPresent()) {
            Artist artist = convertToEntity(artistDto);
            artist = repository.save(artist);
            return convertToDto(artist);
        }
        throw new IllegalArgumentException("Not exists artist with this ID: " + artistDto.getId());
    }

    public void deleteArtist(Long id) {
        Optional<Artist> artistOptional = repository.findById(id);
        if (artistOptional.isPresent()) {
            repository.deleteById(id);
        }
    }

    private ArtistDto convertToDto(Artist artist) {
        ArtistDto artistDto = new ArtistDto();
        BeanUtils.copyProperties(artist, artistDto);
        return artistDto;
    }

    private Artist convertToEntity(ArtistDto artistDto) {
        Artist artist = new Artist();
        BeanUtils.copyProperties(artistDto, artist);
        return artist;
    }
}
