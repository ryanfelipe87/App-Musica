package com.bitzen.appmusica.repositories;

import com.bitzen.appmusica.dtos.MusicAlbumDTO;
import com.bitzen.appmusica.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    @Query("SELECT new com.bitzen.appmusica.dtos.MusicAlbumDTO(m.title, m.track, m.duration, al.title, al.imageUrl, al.year, at.name) " +
            "FROM Music m " +
            "JOIN m.album al " +
            "JOIN al.artist at " +
            "WHERE at.id = ?1")
    List<MusicAlbumDTO> findMusicAndAlbumByArtistId(Long artistId);
}
