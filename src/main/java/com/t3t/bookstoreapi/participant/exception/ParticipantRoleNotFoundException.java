package com.t3t.bookstoreapi.participant.exception;

public class ParticipantRoleNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "존재하지 않는 참여자 역할 입니다.";

    public ParticipantRoleNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
