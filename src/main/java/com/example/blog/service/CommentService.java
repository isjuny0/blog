package com.example.blog.service;

import com.example.blog.dto.request.CommentRequestDto;
import com.example.blog.dto.request.CommentResponseDto;
import com.example.blog.entity.Comment;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.exception.CustomException;
import com.example.blog.exception.ErrorCode;
import com.example.blog.dto.mapper.CommentMapper;
import com.example.blog.kafka.NotificationProducer;
import com.example.blog.repository.CommentLikeRepository;
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
    private final CommentLikeRepository commentLikeRepository;
    private final CommentMapper commentMapper;
    private final NotificationProducer notificationProducer;


    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 댓글 저장 로직
        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .post(post)
                .user(user)
                .build();
        commentRepository.save(comment);

        // Kafka로 알림 메시지 전송
        String message = String.format("'%s' 님이 게시글(%d)에 댓글을 남겼습니다: %s", user.getUsername(), post.getId(), comment.getContent());
        notificationProducer.send(message);

        return commentMapper.toDto(comment);
    }

    public List<CommentResponseDto> getCommentsByPost(Long postId, User currentUser) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        List<Comment> comments = commentRepository.findByPost(post);

        return comments.stream()
                .map(comment -> commentMapper.toDto(comment, currentUser))
                .collect(Collectors.toList());
    }

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        comment.setContent(requestDto.getContent());
        return commentMapper.toDto(commentRepository.save(comment));
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        commentRepository.delete(comment);
    }
}
