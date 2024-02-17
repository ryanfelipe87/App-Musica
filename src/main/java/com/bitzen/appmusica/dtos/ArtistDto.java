package com.bitzen.appmusica.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtistDto{

    private Long id;

    @NotBlank(message = "Cannot be null or empty")
    private String name;

    @NotBlank(message = "Cannot be null or empty")
    private String imageUrl;

}
