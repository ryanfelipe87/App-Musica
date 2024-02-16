package com.bitzen.appmusica.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto{

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String imageUrl;

}
