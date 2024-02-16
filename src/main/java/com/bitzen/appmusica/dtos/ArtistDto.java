package com.bitzen.appmusica.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtistDto{

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String imageUrl;
}
