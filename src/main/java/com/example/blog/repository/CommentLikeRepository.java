package com.example.blog.repository;

import com.example.blog.entity.Comment;
import com.example.blog.entity.CommentLike;
import com.example.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    boolean existsByCommentAndUser(Comment comment, User user);
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
    long countByComment(Comment comment);
}
