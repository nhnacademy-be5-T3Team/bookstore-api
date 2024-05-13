package com.t3t.bookstoreapi.member.exception;

/**
 * 존재하지 않는 회원 등급 정책(MemberGradePolicy)에 대해 조회를 시도하는 경우 발생하는 예외
 *
 * @author hydrationn(박수화)
 */
public class MemberGradePolicyNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 회원 등급 정책입니다.";
    public MemberGradePolicyNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public MemberGradePolicyNotFoundException(Long forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
