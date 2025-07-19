package com.example.blog.controller;

import com.example.blog.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestParam String to) {
        emailService.sendEmail(to, "회원가입 축하합니다", "환영합니다!");
        return ResponseEntity.ok("메일 전송 요청 완료 (비동기 처리)");
    }
}
