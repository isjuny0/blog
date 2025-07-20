package com.example.blog.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationTestController {

    private final NotificationProducer producer;

    @PostMapping("/send-notification")
    public ResponseEntity<String> sendNotification(@RequestParam String message) {
        producer.send(message);
        return ResponseEntity.ok("Kafka 알림 전송 완료");
    }
}
