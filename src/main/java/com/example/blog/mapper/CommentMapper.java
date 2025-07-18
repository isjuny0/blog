package com.example.blog.mapper;

import com.example.blog.dto.CommentResponseDto;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    private final CommentLikeRepository commentLikeRepository;

    public CommentResponseDto toDto(Comment comment, User currentUser) {
        long likeCount = commentLikeRepository.countByComment(comment);
        boolean likedByMe = commentLikeRepository.existsByCommentAndUser(comment, currentUser);

        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .likeCount(likeCount)
                .likedByMe(likedByMe)
                .build();
    }

    public CommentResponseDto toDto(Comment comment) {
        // 좋아요 관련 정보 없이 응답할 때 (create, update 등에서 사용)
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .username(comment.getUser().getUsername())
                .likeCount(null)
                .likedByMe(null)
                .build();
    }
}
