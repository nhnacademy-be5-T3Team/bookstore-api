package com.t3t.bookstoreapi.member.exception;

/**
 * 존재하지 않는 회원 등급을 조회하려고 할 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class MemberGradeNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 회원 등급입니다.";
    public MemberGradeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected MemberGradeNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
