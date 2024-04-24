package com.t3t.bookstoreapi.order.exception;

/**
 * 결제 금액이 주문한 상품들의 총액과 일치하지 않을 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class PaymentAmountMismatchException extends RuntimeException{
    public PaymentAmountMismatchException() {
        super("결제 금액이 주문한 상품들의 총액과 일치하지 않습니다.");
    }
}
