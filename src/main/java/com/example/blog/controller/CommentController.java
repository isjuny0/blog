package com.example.blog.controller;

import com.example.blog.dto.CommentRequestDto;
import com.example.blog.dto.CommentResponseDto;
import com.example.blog.entity.User;
import com.example.blog.security.UserDetailsImpl;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        CommentResponseDto response = commentService.createComment(postId, requestDto, user);
        return ResponseEntity.status(201).body(response);
    }

    // 댓글 목록 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User currentUser = userDetails.getUser();
        List<CommentResponseDto> response = commentService.getCommentsByPost(postId, currentUser);
        return ResponseEntity.ok(response);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommentResponseDto response = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}
