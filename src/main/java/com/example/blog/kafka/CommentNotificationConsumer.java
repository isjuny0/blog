package com.example.blog.kafka;

import com.example.blog.websocket.NotificationWebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommentNotificationConsumer {

    private final NotificationWebSocketService webSocketService;

    @KafkaListener(topics = "comment-notify", groupId = "comment-alert-group")
    public void handleCommentNotification(String message) {
        log.info("Kafka 알림 수신: {}", message);
        webSocketService.sendCommentNotification(message);  // WebSocket으로 전달
    }
}
