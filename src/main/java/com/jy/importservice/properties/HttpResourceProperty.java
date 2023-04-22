package com.jy.importservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @类名 HttpURLProperty
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/17 11:43
 * @版本 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "http")
@PropertySource(value = "classpath:application-dev.yml")
public class HttpResourceProperty {

    private String[] uris;
}