package com.t3t.bookstoreapi.common.exception;

import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.exception.DeliveryNotFoundException;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundException;
import com.t3t.bookstoreapi.shoppingcart.exception.ShoppingCartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

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

    /**
     * 장바구니가 존재하지 않는 경우에 대한 예외 처리 핸들러
     * @param shoppingCartNotFoundException 장바구니가 존재하지 않는 경우 발생하는 예외
     * @see com.t3t.bookstoreapi.shoppingcart.exception.ShoppingCartNotFoundException
     * @see com.t3t.bookstoreapi.shoppingcart.exception.ShoppingCartNotFoundForIdException
     * @return 404 NOT_FOUND - 예외 메시지 반환
     * @author woody35545(구건모)
     */
    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleShoppingCartNotFoundException(ShoppingCartNotFoundException shoppingCartNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(shoppingCartNotFoundException.getMessage()));
    }

    /**
     * 책이 존재하지 않는 경우에 대한 예외 처리 핸들러
     * @param bookNotFoundException 책이 존재하지 않는 경우 발생하는 예외
     * @return 404 NOT_FOUND - 예외 메시지 반환
     * @author woody35545(구건모)
     */
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleBookNotFoundException(BookNotFoundException bookNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(bookNotFoundException.getMessage()));
    }

    /**
     * 잘못된 인자가 전달된 경우에 대한 예외 처리 핸들러
     * @param illegalArgumentException 잘못된 인자가 전달된 경우 발생하는 예외
     * @return 400 BAD_REQUEST - 예외 메시지 반환
     * @author woody35545(구건모)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<Void>().message(illegalArgumentException.getMessage()));
    }

    /**
     * 존재하지 않는 배송에 대한 조회 시도 시 발생하는 예외 처리 핸들러
     * @see com.t3t.bookstoreapi.order.exception.DeliveryNotFoundException
     * @see com.t3t.bookstoreapi.order.exception.DeliveryNotFoundForIdException
     * @param deliveryNotFoundException 존재하지 않는 배송에 대한 조회 시도 시 발생하는 예외
     * @return 404 NOT_FOUND - 예외 메시지 반환
     * @author woody35545(구건모)
     */
    @ExceptionHandler(DeliveryNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleDeliveryNotFoundException(DeliveryNotFoundException deliveryNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(deliveryNotFoundException.getMessage()));
    }


    /**
     * validation 실패 시 발생하는 MethodArgumentNotValidException 예외 처리 핸들러
     * @param  methodArgumentNotValidException validation 실패 시 발생하는 예외
     * @return 400 BAD_REQUEST - 만족하지 못한 validation 항목에 대한 메시지 반환
     * @see org.springframework.web.bind.MethodArgumentNotValidException
     * @author woody35545(구건모)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        return ResponseEntity.badRequest().body(new BaseResponse<String>()
                .message(String.join(" ", methodArgumentNotValidException.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(error -> String.format("* %s  ", error.getDefaultMessage()))
                        .collect(Collectors.joining()))));
    }
}