package com.t3t.bookstoreapi.certcode.exception;

/**
 * 인증 코드가 존재하지 않을 때 발생하는 예외
 *
 * @author wooody35545(구건모)
 */
public class CertCodeNotExistsException extends RuntimeException {
    public CertCodeNotExistsException() {
        super("인증 코드가 존재하지 않습니다.");
    }
}
