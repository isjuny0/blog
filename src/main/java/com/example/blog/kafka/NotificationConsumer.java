package com.example.blog.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void listen(String message) {
        log.info("Kafka 알림 수신: {}", message);
    }
}
