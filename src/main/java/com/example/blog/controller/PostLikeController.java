package com.example.blog.controller;

import com.example.blog.security.UserDetailsImpl;
import com.example.blog.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<String> toggleLike(@PathVariable Long postId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean liked = postLikeService.togglePostLike(postId, userDetails.getUser());
        return ResponseEntity.ok(liked ? "좋아요 추가됨" : "좋아요 취소됨");
    }
}
