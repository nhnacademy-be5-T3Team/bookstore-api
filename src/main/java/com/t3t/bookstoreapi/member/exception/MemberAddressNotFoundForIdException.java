package com.t3t.bookstoreapi.member.exception;

/**
 * 존재하지 않는 회원 주소 식별자로 회원 주소를 조회하려고 할 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class MemberAddressNotFoundForIdException extends MemberAddressNotFoundException {
    public MemberAddressNotFoundForIdException(long memberAddressId) {
        super(new StringBuilder().append("id: ").append(memberAddressId).toString());
    }
}
