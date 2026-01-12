package com.monolith.platform.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 🌍 ENDPOINT PÚBLICO (SIN JWT)
        registry.addMapping("/util/welcomeCoursePlatform")
                .allowedOrigins(
                        "http://localhost:4200",
                        "https://monolito-plataformaonline.onrender.com"
                )
                .allowedMethods("GET")
                .allowedHeaders("*")
                .allowCredentials(false);

        // 🔐 ENDPOINTS PROTEGIDOS (CON JWT)
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://localhost:4200",              // Angular local
                        "https://monolito-backend.onrender.com", // Render (opcional)
                        "https://monolito-plataformaonline.onrender.com/"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Content-Type", "Authorization", "Accept")
                .allowCredentials(true);
    }
}


