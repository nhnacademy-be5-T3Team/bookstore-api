package com.t3t.bookstoreapi.order.exception;

/**
 * 포장을 찾을 수 없을 때 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class PackagingNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "포장을 찾을 수 없습니다.";

    public PackagingNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected PackagingNotFoundException(String forTarget) {
        super(new StringBuilder(DEFAULT_MESSAGE).append(" (").append(forTarget).append(")").toString());
    }

}