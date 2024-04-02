package com.t3t.bookstoreapi.common.exception;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 주문 상태가 존재하지 않는 경우에 대한 예외 처리 핸들러
     *
     * @param orderStatusNotFoundException 주문 상태가 존재하지 않는 경우 발생하는 예외
     * @return 404 NOT_FOUND - 예외 메시지 반환
     * @author woody35545(구건모)
     * @see com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundException
     * @see com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForIdException
     * @see com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForNameException
     */

    @ExceptionHandler(OrderStatusNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleOrderStatusNotFoundException(OrderStatusNotFoundException orderStatusNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(orderStatusNotFoundException.getMessage()));
    }
}
