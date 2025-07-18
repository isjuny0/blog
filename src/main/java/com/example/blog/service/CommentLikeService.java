package com.example.blog.service;

import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentLike;
import com.example.blog.entity.User;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.ErrorCode;
import com.example.blog.repository.CommentLikeRepository;
import com.example.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    public String toggleLike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        // 이미 좋아요를 눌렀는지 확인
        return commentLikeRepository.findByUserAndComment(user, comment)
                .map(existingLike -> {
                    // 존재하면 삭제 (좋아요 취소)
                    commentLikeRepository.delete(existingLike);
                    return "좋아요가 취소되었습니다.";
                })
                .orElseGet(() -> {
                    // 없으면 추가 (좋아요 등록)
                    CommentLike newLike = CommentLike.builder()
                            .user(user)
                            .comment(comment)
                            .build();
                    commentLikeRepository.save(newLike);
                    return "좋아요가 등록되었습니다.";
                });
    }

    public long countLikes(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        return commentLikeRepository.countByComment(comment);
    }
}
