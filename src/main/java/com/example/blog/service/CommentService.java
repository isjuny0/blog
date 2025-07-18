package com.example.blog.service;

import com.example.blog.dto.CommentRequestDto;
import com.example.blog.dto.CommentResponseDto;
import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.ErrorCode;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .post(post)
                .user(user)
                .build();

        commentRepository.save(comment);
        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getUser().getUsername());
    }

    public List<CommentResponseDto> getCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return commentRepository.findByPost(post).stream()
                .map(comment -> new CommentResponseDto(comment.getId(), comment.getContent(), comment.getUser().getUsername()))
                .collect(Collectors.toList());
    }
}
