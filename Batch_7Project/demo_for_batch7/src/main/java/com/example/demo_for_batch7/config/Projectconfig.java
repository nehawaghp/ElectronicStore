package com.example.demo_for_batch7.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Projectconfig{
    @Bean
    public ModelMapper mapper()
    {
        return new ModelMapper();
    }

}


