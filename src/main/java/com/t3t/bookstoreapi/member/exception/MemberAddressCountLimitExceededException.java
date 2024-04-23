package com.t3t.bookstoreapi.member.exception;

/**
 * 회원 주소의 개수 제한을 초과했을 때 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class MemberAddressCountLimitExceededException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "회원 주소의 개수 제한을 초과하였습니다.";

    public MemberAddressCountLimitExceededException() {
        super(DEFAULT_MESSAGE);
    }
}
