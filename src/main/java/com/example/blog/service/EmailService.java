package com.example.blog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Async
    public void sendEmail(String to, String subject, String content) {
        log.info("메일 전송 시작: {} [스레드: {}]", to, Thread.currentThread().getName());
        try {
            Thread.sleep(3000); // 실제 전송 대신 3초 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("메일 전송 완료: {}", to);
    }
}
