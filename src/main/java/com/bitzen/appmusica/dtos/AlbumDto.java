package com.bitzen.appmusica.dtos;

import com.bitzen.appmusica.models.Artist;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String year;

    @NotBlank
    private String imageUrl;

    private Artist artist;

    public AlbumDto(Long id, Long id_null, String title, String year, String imageUrl) {
    }
}
