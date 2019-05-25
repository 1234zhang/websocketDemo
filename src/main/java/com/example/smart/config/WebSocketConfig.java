package com.example.smart.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author Brandon.
 * @date 2019/5/20.
 * @time 15:03.
 */

@Configuration
@Slf4j
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        log.info("serverEndpointExporter be created");
        return new ServerEndpointExporter();
    }
}
