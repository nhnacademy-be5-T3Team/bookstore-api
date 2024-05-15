package com.t3t.bookstoreapi.book.exception;

public class ImageDataStorageException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "이미지 데이터 저장 중 오류가 발생했습니다. 나중에 다시 시도해주세요.";

    /**
     *
     * @param cause   원인 예외
     * @author Yujin-nKim(김유진)
     */
    public ImageDataStorageException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
