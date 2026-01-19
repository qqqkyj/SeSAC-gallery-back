package com.example.galleryback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Primary // 구현체 중 이 클래스를 최우선으로 주입
@ConditionalOnProperty(name = "cloud.aws.s3.bucket")//특정 설정값(Property)이 존재하거나 특정 조건일 때만 이 클래스를 Bean으로 등록
@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService{

    private final S3Client s3Client;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploud(MultipartFile file)
    {
        try
        {
            String key = "uploads/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key; //실제 저장 위치
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String imageUrl) {
        String prefix = "https://" + bucket + ".s3." + region + ".amazonaws.com/";
        //https://my-gallery-s3-kyj.s3.ap-northeast-2.amazonaws.com/uploads/91654294-85f9-4589-ab2a-975853cb7ee2_%EB%BD%80.png
        //key = uploads/91654294-85f9-4589-ab2a-975853cb7ee2_%EB%BD%80.png
        String key = imageUrl.replace(prefix,"");

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }
}
