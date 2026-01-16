package com.example.galleryback.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PhotoRequest {
    @NotBlank
    private String title;
    private String description;
}
