package com.bitzen.appmusica.dtos;

import com.bitzen.appmusica.models.Album;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicDto {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String duration;

    @NotNull
    @Positive
    private Integer track;

    private Album album;
}
