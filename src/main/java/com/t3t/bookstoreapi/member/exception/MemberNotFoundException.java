package com.t3t.bookstoreapi.member.exception;

public class MemberNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 회원(Member) 입니다.";

    public MemberNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
