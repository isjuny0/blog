package com.example.blog.controller;

import com.example.blog.security.UserDetailsImpl;
import com.example.blog.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 좋아요 토글 (등록 / 취소)
    @PostMapping("/{commentId}/like")
    private String toggleLike(@PathVariable Long commentId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentLikeService.toggleLike(commentId, userDetails.getUser());
    }

    // 좋아요 개수 조회
    @GetMapping("/{commentId}/likes")
    private long countLikes(@PathVariable Long commentId) {
        return commentLikeService.countLikes(commentId);
    }
}
