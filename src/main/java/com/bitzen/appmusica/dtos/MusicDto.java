package com.bitzen.appmusica.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicDto {

    private Long id;

    @NotBlank(message = "Cannot be null or empty")
    private String title;

    @NotBlank(message = "Cannot be null or empty")
    private String duration;

    @NotNull(message = "Cannot be null")
    @Positive
    private Integer track;

    private Long albumId;

}
