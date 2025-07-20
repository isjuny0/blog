package com.example.blog.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendCommentNotification(String message) {
        messagingTemplate.convertAndSend("/topic/comments", message);   // 클라이언트가 구독할 WebSocket 채널
    }
}
