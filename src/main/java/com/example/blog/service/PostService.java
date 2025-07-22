package com.example.blog.service;

import com.example.blog.dto.request.PostRequestDto;
import com.example.blog.dto.response.PostResponseDto;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.ErrorCode;
import com.example.blog.dto.mapper.PostMapper;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;

    public PostResponseDto create(PostRequestDto dto, User user) {
        Post post = postMapper.toEntity(dto);
        post.setUser(user);

        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    public List<PostResponseDto> findAll() {
        return postRepository.findAll().stream()
                .map(postMapper::toDto)
                .toList();
    }

//    @Cacheable(value = "post", key = "#postId")
    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findWithUserById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        return postMapper.toDto(post);
    }

    @CachePut(value = "post", key = "#postId")
    public PostResponseDto update(Long postId, PostRequestDto dto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setUpdatedAt(LocalDateTime.now());
        return postMapper.toDto(postRepository.save(post));
    }


    @CacheEvict(value = "post", key = "#postId")
    public void delete(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        if (!post.getUser().getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        postRepository.delete(post);
    }
}
