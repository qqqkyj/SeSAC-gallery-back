package com.example.galleryback.service;

import com.example.galleryback.dto.PhotoResponse;
import com.example.galleryback.entity.Photo;
import com.example.galleryback.repository.PhotoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
public class PhotoServiceTest {

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private FileStorageService fileStorageService;

    @InjectMocks
    private PhotoService photoService;

    @Test
    @DisplayName("사진 목록 조회")
    void findAll() {
        List<Photo> photos = List.of(
            createPhoto("사진1", "/uploads/1.jpg"),
            createPhoto("사진2", "/uploads/2.jpg")
        );

        given(photoRepository.findAll()).willReturn(photos);
        List<PhotoResponse> result = photoService.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("사진 상세 조회")
    void findById() {
        Photo photo = createPhoto("test image", "1.jpg");
        given(photoRepository.findById(1L)).willReturn(Optional.of(photo));
        PhotoResponse result = photoService.findById(1L);
        assertThat(result.getTitle()).isEqualTo(photo.getTitle());
        assertThat(result.getImageUrl()).isEqualTo(photo.getImageUrl());
    }

    private Photo createPhoto(String title, String imageUrl) {
        return Photo.builder().title(title).imageUrl(imageUrl).build();
    }
}
