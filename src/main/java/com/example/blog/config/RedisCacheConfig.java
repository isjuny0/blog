package com.example.blog.config;

import com.example.blog.dto.response.PostResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {     // Redis에 데이터를 JSON 형식으로 저장

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Jackson2JsonRedisSerializer<PostResponseDto> postSerializer =
                new Jackson2JsonRedisSerializer<>(PostResponseDto.class);
        postSerializer.setObjectMapper(objectMapper);

        RedisCacheConfiguration postCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(SerializationPair.fromSerializer(postSerializer))
                .entryTtl(Duration.ofMinutes(10));

        Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
        cacheConfigs.put("post", postCacheConfig);

        return RedisCacheManager.builder(connectionFactory)
                .withInitialCacheConfigurations(cacheConfigs)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()) // 기본값
                .build();
    }
}
