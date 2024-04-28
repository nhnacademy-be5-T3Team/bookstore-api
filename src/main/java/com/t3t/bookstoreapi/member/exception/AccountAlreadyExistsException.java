package com.t3t.bookstoreapi.member.exception;

/**
 * 이미 존재하는 계정을 생성하려고 할 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class AccountAlreadyExistsException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "이미 존재하는 계정입니다.";
    public AccountAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    protected AccountAlreadyExistsException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
