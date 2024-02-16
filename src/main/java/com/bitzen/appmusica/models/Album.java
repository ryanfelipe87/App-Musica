package com.bitzen.appmusica.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Album")
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "year_launch", nullable = false)
    private String year;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    private Artist artist;

    public Album(Long id, Long id_null, String title, String year, String imageUrl) {
    }
}
