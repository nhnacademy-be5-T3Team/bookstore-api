package com.t3t.bookstoreapi.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 메시지 전송을 위한 속성을 저장하는 프로퍼티 클래스
 *
 * @author woody35545(구건모)
 */
@ConfigurationProperties(prefix = "t3t.message")
@Getter
@Setter
public class MessageSenderProperties {
    private String hookUrl;
}
