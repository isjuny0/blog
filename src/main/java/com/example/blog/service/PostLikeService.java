package com.example.blog.service;

import com.example.blog.entity.Post;
import com.example.blog.entity.PostLike;
import com.example.blog.entity.User;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.ErrorCode;
import com.example.blog.repository.PostLikeRepository;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public boolean togglePostLike(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Optional<PostLike> optionalLike = postLikeRepository.findByPostAndUser(post, user);

        if (optionalLike.isPresent()) {
            postLikeRepository.delete(optionalLike.get());
            return false;
        } else {
            postLikeRepository.save(new PostLike(post, user));
            return true;
        }
    }

    public long getLikeCount(Post post) {
        return postLikeRepository.countByPost(post);
    }

    public boolean isPostLikedByUser(Post post, User user) {
        return postLikeRepository.existsByPostAndUser(post, user);
    }
}
