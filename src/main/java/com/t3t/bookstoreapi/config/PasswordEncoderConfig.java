package com.t3t.bookstoreapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class PasswordEncoderConfig {

    /**
     * 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈<br>
     * 회원 가입시 사용자의 비밀번호를 암호화하여 저장하기 위해 사용된다.
     * @author woody35545(구건모)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
