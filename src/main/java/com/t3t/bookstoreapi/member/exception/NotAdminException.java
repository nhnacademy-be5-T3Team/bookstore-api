package com.t3t.bookstoreapi.member.exception;

public class NotAdminException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "관리자 권한이 없습니다.";

    public NotAdminException() {
        super(DEFAULT_MESSAGE);
    }

    public NotAdminException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }

}