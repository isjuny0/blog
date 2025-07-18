package com.example.blog.service;

import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentLike;
import com.example.blog.entity.User;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.ErrorCode;
import com.example.blog.repository.CommentLikeRepository;
import com.example.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public boolean toggleLike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        return commentLikeRepository.findByUserAndComment(user, comment)
                .map(existingLike -> {
                    commentLikeRepository.delete(existingLike);
                    return false; // 좋아요 취소됨
                })
                .orElseGet(() -> {
                    commentLikeRepository.save(CommentLike.builder().user(user).comment(comment).build());
                    return true; // 좋아요 등록됨
                });
    }

    public long countLikes(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        return commentLikeRepository.countByComment(comment);
    }
}
