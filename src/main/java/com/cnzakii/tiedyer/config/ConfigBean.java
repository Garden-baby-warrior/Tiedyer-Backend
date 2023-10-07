package com.cnzakii.tiedyer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 手动注入Bean
 *
 * @author Zaki
 * @since 2023-09-27
 **/
@Configuration
public class ConfigBean {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
