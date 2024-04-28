package com.t3t.bookstoreapi.member.exception;

/**
 * 등급명에 해당하는 회원 등급이 존재하지 않을 때 발생하는 예외
 * @see MemberGradeNotFoundException
 * @author woody35545(구건모)
 */
public class MemberGradeNotFoundForNameException extends MemberGradeNotFoundException{
    public MemberGradeNotFoundForNameException(String forTarget) {
        super(String.format("name: %s", forTarget));
    }
}
