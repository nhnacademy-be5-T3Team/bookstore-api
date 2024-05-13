package com.t3t.bookstoreapi.member.exception;

/**
 * 회원 계정의 비밀번호 검증 시, 비밀번호가 일치하지 않을 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class AccountPasswordNotMatchException extends RuntimeException {
    public AccountPasswordNotMatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
