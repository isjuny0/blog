package com.example.blog.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentNotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendCommentNotification(String message) {
        kafkaTemplate.send("comment-notify", message);
    }
}
