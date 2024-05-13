package com.t3t.bookstoreapi.certcode.exception;

/**
 * 인증 코드가 일치하지 않을 때 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class CertCodeNotMatchesException extends RuntimeException {
    public CertCodeNotMatchesException() {
        super("인증 코드가 일치하지 않습니다.");
    }
}
