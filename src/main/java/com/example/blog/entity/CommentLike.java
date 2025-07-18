package com.example.blog.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "comment_like",
        // 복합 유니크 제약조건 -> 한 사용자가 같은 댓글에 여러 번 좋아요하지 못하도록
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "comment_id"})}
)
public class CommentLike {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

}
