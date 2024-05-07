package com.t3t.bookstoreapi.membergradepolicies.exception;

public class MemberGradePolicyNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 회원 등급 정책입니다.";
    public MemberGradePolicyNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public MemberGradePolicyNotFoundException(Long forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
