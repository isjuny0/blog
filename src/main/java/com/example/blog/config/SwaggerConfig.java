package com.example.blog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // API 기본 정보 설정
    @Bean
    public OpenAPI blogOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog API")
                        .description("Blog 프로젝트의 REST API 명세서입니다.")
                        .version("v1.0.0"));
    }

    // 그룹 API 설정 (선택)
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("blog-api")
                .pathsToMatch("/api/**")
                .build();
    }
}
