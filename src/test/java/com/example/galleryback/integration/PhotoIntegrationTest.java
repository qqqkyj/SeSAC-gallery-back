package com.example.galleryback.integration;

import com.example.galleryback.entity.Photo;
import com.example.galleryback.repository.PhotoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class PhotoIntegrationTest {
    private final MockMvc mockMvc;
    private final PhotoRepository photoRepository;
    private final ObjectMapper objectMapper; //json 변환

    @Test
    @DisplayName("사진 목록 조회")
    void getPhotos() throws Exception {
        photoRepository.save(
                Photo.builder().title("image1").imageUrl("1.jpg").build()
        );
        photoRepository.save(
                Photo.builder().title("image2").imageUrl("2.jpg").build()
        );

        mockMvc.perform(get("/api/photos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("image1"));
    }
}
