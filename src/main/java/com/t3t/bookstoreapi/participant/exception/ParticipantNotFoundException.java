package com.t3t.bookstoreapi.participant.exception;

public class ParticipantNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "존재하지 않는 참여자 입니다.";

    public ParticipantNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

