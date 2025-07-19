package com.example.blog.dto.mapper;

import com.example.blog.dto.request.PostRequestDto;
import com.example.blog.dto.response.PostResponseDto;
import com.example.blog.entity.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    public Post toEntity(PostRequestDto dto) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public PostResponseDto toDto(Post entity) {
        return PostResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .username(entity.getUser().getUsername())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
