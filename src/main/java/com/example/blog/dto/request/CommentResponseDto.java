package com.example.blog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private String username;

    private Long likeCount;
    private Boolean likedByMe;
}
