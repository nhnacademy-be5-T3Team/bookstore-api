package com.t3t.bookstoreapi.order.exception;

/**
 * 포장 식별자에 해당하는 포장 정보를 찾을 수 없을 때 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class PackagingNotFoundForIdException extends PackagingNotFoundException {

    public PackagingNotFoundForIdException(Long id) {
        super(new StringBuilder("id: ").append(id).toString());
    }
}