package com.example.ShanruanShopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MyInterceptorConfig extends WebMvcConfigurationSupport {
    private String UPLOAD_FOLDER = "C:\\Users\\86183\\Desktop\\数据库课设-山软购物平台\\ShanruanShopping_front\\ShanruanShopping\\src\\assets\\image";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("file:" + UPLOAD_FOLDER + "/");
    }
}
