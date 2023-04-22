package com.jy.importservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @类名 CtlContentProperty
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/22 17:47
 * @版本 1.0
 */
@Getter
@Setter
@PropertySource(value = "classpath:application-dev.yml")
@ConfigurationProperties(prefix = "ctl")
@Component
public class CtlContentProperty {

    private String content;
}
