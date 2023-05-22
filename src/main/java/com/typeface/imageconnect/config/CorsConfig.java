package com.typeface.imageconnect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class is required because front end and backend are on the same repo and
 * is due to the browser's security mechanism that restricts cross-origin HTTP requests.
 * To avoid that this class was added
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")  // Add the path pattern for your API endpoints
                        .allowedOrigins("http://localhost:4200")  // Add the allowed origin
                        .allowedMethods("GET", "POST", "OPTIONS", "PUT", "DELETE")  // Add the allowed methods
                        .allowedHeaders("Content-Type")  // Add the allowed headers
                        .allowCredentials(true);  // Allow including cookies in the requests
            }
        };
    }
}


