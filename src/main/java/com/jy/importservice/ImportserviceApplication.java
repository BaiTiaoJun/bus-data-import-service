package com.jy.importservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.jy.importservice.mapper")
@SpringBootApplication
public class ImportserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportserviceApplication.class, args);
    }

}
