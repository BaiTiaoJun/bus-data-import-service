package com.jy.importservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @类名 WebsocketConfig
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/23 11:28
 * @版本 1.0
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
