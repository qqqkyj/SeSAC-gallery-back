package com.example.galleryback.service;

import com.example.galleryback.dto.PhotoRequest;
import com.example.galleryback.dto.PhotoResponse;
import com.example.galleryback.entity.Photo;
import com.example.galleryback.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final FileStorageService fileStorageService;

    public List<PhotoResponse> findAll(){
        return photoRepository.findAll()
                .stream()
                .map(PhotoResponse::from)
                .toList();
    }

    public PhotoResponse findById(Long id){
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Photo not found"));
        return PhotoResponse.from(photo);
    }

    @Transactional
    public PhotoResponse save(PhotoRequest request, MultipartFile file){
        String imageUrl = fileStorageService.uploud(file);

        Photo photo = Photo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .imageUrl(imageUrl)
                .build();

        Photo saved = photoRepository.save(photo);
        return PhotoResponse.from(saved);
    }

    @Transactional
    public void delete(Long id){
        Photo photo = photoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Photo not found"));
        fileStorageService.delete(photo.getImageUrl());
        photoRepository.delete(photo);
    }
}
