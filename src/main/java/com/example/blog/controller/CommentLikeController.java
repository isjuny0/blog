package com.example.blog.controller;

import com.example.blog.security.UserDetailsImpl;
import com.example.blog.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    // 좋아요 토글 (등록 / 취소)
    @PostMapping("/{commentId}/like")
    private ResponseEntity<String> toggleLike(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean liked = commentLikeService.toggleLike(commentId, userDetails.getUser());
        return ResponseEntity.ok(liked ? "좋아요가 등록되었습니다." : "좋아요가 취소되었습니다.");
    }

    // 좋아요 개수 조회
    @GetMapping("/{commentId}/likes")
    private long countLikes(@PathVariable Long commentId) {
        return commentLikeService.countLikes(commentId);
    }
}
