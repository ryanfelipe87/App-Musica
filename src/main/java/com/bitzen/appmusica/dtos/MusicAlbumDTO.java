package com.bitzen.appmusica.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MusicAlbumDTO {

    @JsonProperty("musicTitle")
    private String musicTitle;

    @JsonProperty("track")
    private Integer track;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("albumTitle")
    private String albumTitle;

    @JsonProperty("albumImageUrl")
    private String albumImageUrl;

    @JsonProperty("albumYearLaunch")
    private String albumYearLaunch;

    @JsonProperty("artistName")
    private String artistName;
}
