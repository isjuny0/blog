package com.example.blog.repository;

import com.example.blog.entity.Post;
import com.example.blog.entity.PostLike;
import com.example.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostAndUser(Post post, User user);
    Optional<PostLike> findByPostAndUser(Post post, User user);
    long countByPost(Post post);
}
