package com.example.galleryback.beantest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootTest // 또는 @ContextConfiguration
class ApplicationContextInfoTest {
    @Autowired
    ApplicationContext applicationContext;

    @Test
    void findBean() {
        // 등록된 모든 Bean 이름 조회
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        System.out.println("Bean 이름들: " + Arrays.toString(beanDefinitionNames));

//        // 특정 타입의 Bean 조회
//        Map<String, TV> beansOfType = applicationContext.getBeansOfType(TV.class);
//        System.out.println("TV 타입 Bean들: " + beansOfType);
    }
}