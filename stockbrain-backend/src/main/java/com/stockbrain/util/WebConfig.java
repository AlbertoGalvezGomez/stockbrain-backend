package com.stockbrain.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/productos/**")
                .addResourceLocations("file:C:/Users/Alberto/Desktop/stockbrain-backend/stockbrain-backend/uploads/productos/");
    }
}