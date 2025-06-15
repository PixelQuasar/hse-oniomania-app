package com.example.gateway.websocket;

import com.example.gateway.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StatusUpdateHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(StatusUpdateHandler.class);
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = getUserIdFromSession(session);
        if (userId == null) {
            logger.warn("Closing session with invalid URI: {}", session.getUri());
            session.close(CloseStatus.BAD_DATA.withReason("User ID must be a number"));
            return;
        }

        sessions.put(userId, session);
        logger.info("WebSocket session opened for user: {}. Session ID: {}", userId, session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            sessions.remove(userId);
            logger.info("WebSocket session closed for user: {}. Reason: {}", userId, status);
        }
    }

    public void sendStatus(Long userId, OrderStatus status) {
        WebSocketSession session = sessions.get(userId);

        if (session != null && session.isOpen()) {
            try {
                logger.info("Sending status '{}' to user {}", status.name(), userId);
                synchronized (session) {
                    session.sendMessage(new TextMessage(status.name()));
                }
            } catch (IOException e) {
                logger.error("Failed to send message to user {}: {}", userId, e.getMessage());
            }
        } else {
            logger.warn("Could not send status. No active session found for user {}", userId);
        }
    }

    private Long getUserIdFromSession(WebSocketSession session) {
        if (session.getUri() == null) {
            return null;
        }
        String path = session.getUri().getPath();
        try {
            return Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }
}
