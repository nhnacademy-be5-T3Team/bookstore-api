package com.t3t.bookstoreapi.member.exception;

/**
 * 이미 존재하는 계정 id로 계정을 생성하려고 할 때 발생하는 예외
 * @see AccountAlreadyExistsException
 * @author woody35545(구건모)
 */
public class AccountAlreadyExistsForIdException extends AccountAlreadyExistsException{
    public AccountAlreadyExistsForIdException(String forTarget) {
        super(String.format("id: %s", forTarget));
    }
}
