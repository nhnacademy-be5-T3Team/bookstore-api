package com.t3t.bookstoreapi.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger 설정을 저장하는 프로퍼티 클래스<br>
 * OpenAPI 빈 설정에 사용된다.
 * @see com.t3t.bookstoreapi.config.SwaggerConfig
 * @see io.swagger.v3.oas.models.OpenAPI
 * @author woody35545(구건모)
 */
@Data
@ConfigurationProperties(prefix = "t3t.swagger")
public class SwaggerProperties {
    private String title;
    private String description;
    private String version;
}
