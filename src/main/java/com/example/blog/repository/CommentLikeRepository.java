package com.example.blog.repository;

import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentLike;
import com.example.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    // 좋아요 여부 확인
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    // 댓글 좋아요 개수 집계
    long countByComment(Comment comment);
    // 좋아요 취소
    void deleteByUserAndComment(User user, Comment comment);
}
