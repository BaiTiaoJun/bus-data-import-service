package com.jy.importservice.config;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @类名 DateConvertConfig
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 14:40
 * @版本 1.0
 */
@Configuration
public class DateConvertConfig implements Jackson2ObjectMapperBuilderCustomizer {

    @Value("${date.format}")
    private String dateFormat;

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        DateDeserializers.DateDeserializer instance = DateDeserializers.DateDeserializer.instance;
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        DateDeserializers.DateDeserializer dateDeserializer = new DateDeserializers.DateDeserializer(instance, format, null);
        jacksonObjectMapperBuilder.deserializerByType(Date.class, dateDeserializer);
    }
}
