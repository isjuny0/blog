package com.example.blog.integration;

import com.example.blog.dto.PostRequestDto;
import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;

    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtUtil jwtUtil;
    @Autowired PostRepository postRepository;

    private String jwtToken;
    private User testUser;

    @BeforeEach
    void setUp() {
        // 사용자 생성
        testUser = userRepository.save(User.builder()
                .username("testUser")
                .password(passwordEncoder.encode("1234"))
                .build());

        jwtToken = jwtUtil.createAccessToken(testUser.getUsername());
    }

    @Test
    void createPost_success() throws Exception {
        PostRequestDto dto = new PostRequestDto("제목", "내용");

        mockMvc.perform(post("/api/posts")
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("제목"));
    }

    @Test
    void  updatePost_onlyAuthorized() throws Exception {
        // 게시글 생성
        Post post = Post.builder()
                .title("원래 제목")
                .content("원래 내용")
                .user(testUser)
                .build();
        postRepository.save(post);

        PostRequestDto updateDto = new PostRequestDto("수정된 제목", "수정된 내용");

        mockMvc.perform(put("/api/posts/" + post.getId())
                .header("Authorization", jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 제목"));
    }

    @Test
    void deletePost_onlyAuthorized() throws Exception {
        Post post = Post.builder()
                .title("삭제할 글")
                .content("삭제 내용")
                .user(testUser)
                .build();
        postRepository.save(post);

        mockMvc.perform(delete("/api/posts/" + post.getId())
                .header("Authorization", jwtToken))
                .andExpect(status().isNoContent());
    }
}
