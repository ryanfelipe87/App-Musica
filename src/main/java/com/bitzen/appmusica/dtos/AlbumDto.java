package com.bitzen.appmusica.dtos;

import com.bitzen.appmusica.models.Artist;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String year;

    @NotBlank
    private String imageUrl;

    private Artist artist;
}
