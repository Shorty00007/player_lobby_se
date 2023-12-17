package com.example.player_lobby;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public static RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
