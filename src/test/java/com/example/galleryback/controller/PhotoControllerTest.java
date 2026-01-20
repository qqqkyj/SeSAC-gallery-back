package com.example.galleryback.controller;

import com.example.galleryback.dto.PhotoResponse;
import com.example.galleryback.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(PhotoController.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class PhotoControllerTest {
    private final MockMvc mockMvc;

    @MockBean
    private PhotoService photoService;

    @Test
    @DisplayName("GET /api/photos")
    void list() throws Exception {
        List<PhotoResponse> photos = List.of(
                PhotoResponse.builder()
                        .id(1L)
                        .title("img1")
                        .imageUrl("1.jpg")
                        .build(),
                PhotoResponse.builder()
                        .id(2L)
                        .title("img2")
                        .imageUrl("2.jpg")
                        .build()
        );
        given(photoService.findAll()).willReturn(photos);

        mockMvc.perform(get("/api/photos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("img1"));
//                 $:전체 테이터
//                {id:1, title: "test", }
//                {id:2, title: "hello", }
    }

}
