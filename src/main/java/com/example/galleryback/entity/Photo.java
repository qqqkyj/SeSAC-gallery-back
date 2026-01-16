package com.example.galleryback.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "photo")
@Getter
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 100)
    private String description;
    @Column(nullable = false, length = 100)
    private String imageUrl;

    @Builder
    public Photo(String title, String description, String imageUrl){
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }
}
