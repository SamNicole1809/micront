package com.micront.printing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MicrontRestTemplateConfig {

    @Autowired
    private MicrontRestTemplateErrorHandler restTemplateErrorHandler;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(restTemplateErrorHandler);
        return restTemplate;
    }

}
