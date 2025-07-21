package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(
        name = "Post.withUser",
        attributeNodes = @NamedAttributeNode("user")    // user 필드를 즉시 로딩하라는 의미
)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public void setUser(User user) {
        this.user = user;
    }
}
