package com.example.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String content;
    private String username;
}
