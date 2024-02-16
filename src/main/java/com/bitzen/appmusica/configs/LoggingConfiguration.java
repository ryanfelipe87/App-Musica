package com.bitzen.appmusica.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

@Configuration
public class LoggingConfiguration {

    @Bean
    public Logger logger(){
        return Logger.getLogger("com.bitzen.appmusica.patterns");
    }
}
