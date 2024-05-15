package com.t3t.bookstoreapi.pointdetail.exception;

/**
 * 존재하지 않는 포인트 내역(PointDetails)에 대해 조회를 시도하는 경우 발생하는 예외
 * @author hydrationn(박수화)
 */
public class PointDetailNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "포인트 내역이 존재하지 않습니다.";
    public PointDetailNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PointDetailNotFoundException(String pointDetailType) {
        super(String.format("%s (%s)", pointDetailType, DEFAULT_MESSAGE));
    }
}

