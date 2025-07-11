package com.example.gateway.config;

import com.example.gateway.websocket.StatusUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final StatusUpdateHandler statusUpdateHandler;

    @Autowired
    public WebSocketConfig(StatusUpdateHandler statusUpdateHandler) {
        this.statusUpdateHandler = statusUpdateHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(statusUpdateHandler, "/status-updates/{userId}");
    }
}
