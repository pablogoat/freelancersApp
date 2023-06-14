package com.FreelancersBackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Component
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/api/**") // Wskazuje ścieżkę API, dla której ma być zastosowana konfiguracja CORS
                .allowedOrigins("http://localhost:3000") // Wymień tutaj adresy, z których akceptowane będą żądania CORS (np. adres frontendu)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Określa dozwolone metody HTTP
                .allowedHeaders("*") // Określa dozwolone nagłówki
                .allowCredentials(false); // Umożliwia przesyłanie ciasteczek i nagłówka autoryzacji
    }
}
