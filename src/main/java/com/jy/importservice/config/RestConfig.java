package com.jy.importservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @类名 RestConfig
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 10:31
 * @版本 1.0
 */
@Configuration
public class RestConfig {

    @Value("${http.root}")
    private String root;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.setConnectTimeout(Duration.ofSeconds(10000))
                .setReadTimeout(Duration.ofSeconds(10000))
                .rootUri(root)
                .build();
    }
}
