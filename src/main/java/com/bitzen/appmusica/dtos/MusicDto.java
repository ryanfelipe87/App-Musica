package com.bitzen.appmusica.dtos;

import com.bitzen.appmusica.models.Album;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDto {

    private Long id;
    private String title;
    private String duration;
    private Integer track;
    private Album album;
}
