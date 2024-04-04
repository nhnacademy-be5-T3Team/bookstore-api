package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.property.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger3 설정 클래스
 * @see com.t3t.bookstoreapi.property.SwaggerProperties
 * @author woody35545(구건모)
 */
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion()));
    }
}