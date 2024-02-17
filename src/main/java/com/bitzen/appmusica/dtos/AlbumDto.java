package com.bitzen.appmusica.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumDto {

    private Long id;

    @NotBlank(message = "Cannot be null or empty")
    private String title;

    @NotBlank(message = "Cannot be null or empty")
    private String year;

    @NotBlank(message = "Cannot be null or empty")
    private String imageUrl;

    @NotNull(message = "Cannot be null")
    private Long artistId;

    public AlbumDto(Long id, String title, String year, String imageUrl) {
    }
}
