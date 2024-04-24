package com.t3t.bookstoreapi.member.exception;

/**
 * 존재하지 않는 회원 주소를 조회하려고 할 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class MemberAddressNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 회원 주소 입니다.";

    public MemberAddressNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected MemberAddressNotFoundException(String forTarget) {
        super(new StringBuilder().append(DEFAULT_MESSAGE).append(" (").append(forTarget).append(")").toString());
    }
}
