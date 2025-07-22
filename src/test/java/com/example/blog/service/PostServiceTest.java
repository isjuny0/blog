package com.example.blog.service;

import com.example.blog.dto.mapper.PostMapper;
import com.example.blog.dto.request.PostRequestDto;
import com.example.blog.dto.response.PostResponseDto;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock private PostRepository postRepository;
    @Mock private PostMapper postMapper;

    @InjectMocks private PostService postService;

    @Test
    void create_success() {
        // given
        User user = User.builder()
                .id(1L)
                .username("user1")
                .password("1234")
                .build();

        PostRequestDto dto = new PostRequestDto("title", "content");

        Post postEntity = Post.builder()
                .title("title")
                .content("content")
                .build();

        Post savedPost = Post.builder()
                .id(1L)
                .title("title")
                .content("content")
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        PostResponseDto responseDto = new PostResponseDto(
                1L,
                "title",
                "content",
                "user1",
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );

        // stub
        given(postMapper.toEntity(dto)).willReturn(postEntity);
        given(postRepository.save(postEntity)).willReturn(savedPost);
        given(postMapper.toDto(savedPost)).willReturn(responseDto);

        // when
        PostResponseDto result = postService.create(dto, user);

        // then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("title");
        assertThat(result.getContent()).isEqualTo("content");
        assertThat(result.getUsername()).isEqualTo("user1");
        assertThat(result.getCreatedAt()).isNotNull();
        assertThat(result.getUpdatedAt()).isNotNull();
    }
}
