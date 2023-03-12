package com.topsycreed;

import com.topsycreed.controllers.SuperheroController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class CommonConfiguration {
    @Bean
    public SuperheroController superheroController() {
        return new SuperheroController();
    }
}
