package com.example.galleryback.service;

import com.example.galleryback.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public String uploud(MultipartFile file) {
        try{
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            return "/uploads/" + fileName;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String imageUrl) {
        try{
            Path filePath = Paths.get(uploadPath + imageUrl.replace("/uploads",""));
            Files.deleteIfExists(filePath);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
