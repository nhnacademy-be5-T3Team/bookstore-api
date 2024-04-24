package com.t3t.bookstoreapi.member.exception;

/**
 * 존재하지 않는 회원 식별자로 회원을 조회하려고 할 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class MemberNotFoundForIdException extends MemberNotFoundException {
    public MemberNotFoundForIdException(long memberId) {
        super(new StringBuilder().append("id: ").append(memberId).toString());
    }
}
