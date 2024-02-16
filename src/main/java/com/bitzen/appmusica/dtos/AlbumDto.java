package com.bitzen.appmusica.dtos;

import com.bitzen.appmusica.models.Artist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto {

    private Long id;
    private String title;
    private String year;
    private String imageUrl;
    private Artist artist;
}
