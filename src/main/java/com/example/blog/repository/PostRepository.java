package com.example.blog.repository;

import com.example.blog.entity.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

//    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.id = :id")   // 게시글과 작성자를 하나의 쿼리로 조회
//    Optional<Post> findByIdWithUser(@Param("id") Long id);  // 기존 findById() 대신 이 메서드를 사용해야 N+1이 발생하지 않음

    // @EntityGraph로 연관된 User 엔티티를 자동으로 fetch join처럼 조회
    // type = LOAD: 지정된 속성(user)은 즉시 로딩, 나머지는 기본 설정을 따름
    @EntityGraph(value = "Post.withUser", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Post> findWithUserById(Long id);
}
