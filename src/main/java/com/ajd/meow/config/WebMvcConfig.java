package com.ajd.meow.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private String resourcePath= "/upload/**"; //뷰에서 접근할 경로
    private String savePaths = "file:///C:/Users/JunHee/develop/Github/meow/src/main/resources/static/files"; //실제파일 경로

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(resourcePath)
                .addResourceLocations(savePaths);
    }
}
