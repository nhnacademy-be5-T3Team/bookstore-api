package com.t3t.bookstoreapi.common.exception;

import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.category.exception.CategoryNotFoundException;
import com.t3t.bookstoreapi.member.exception.AccountAlreadyExistsException;
import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundException;
import com.t3t.bookstoreapi.member.exception.MemberGradeNotFoundForNameException;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.exception.DeliveryNotFoundException;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundException;
import com.t3t.bookstoreapi.shoppingcart.exception.ShoppingCartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
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
     * 카테고리가 존재하지 않는 경우에 대한 예외 처리 핸들러
     * @param  categoryNotFoundException 카테고리가 존재하지 않는 경우 발생하는 예외
     * @return 404 NOT_FOUND - 예외 메시지 반환
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleCategoryNotFoundException(CategoryNotFoundException categoryNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(categoryNotFoundException.getMessage()));
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
     * 회원 계정이 이미 존재하는 경우에 대한 예외 처리 핸들러
     * @param accountAlreadyExistsException 회원 계정이 이미 존재하는 경우 발생하는 예외
     * @see com.t3t.bookstoreapi.member.exception.AccountAlreadyExistsException
     * @return 409 CONFLICT - 예외 메시지 반환
     * @author woody35545(구건모)
     */
    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<BaseResponse<Void>> handleAccountAlreadyExistsException(AccountAlreadyExistsException accountAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BaseResponse<Void>().message(accountAlreadyExistsException.getMessage()));
    }

    /**
     * 회원 등급이 존재하지 않는 경우에 대한 예외 처리 핸들러
     * @param memberGradeNotFoundForNameException 회원 등급이 존재하지 않는 경우 발생하는 예외
     * @see com.t3t.bookstoreapi.member.exception.MemberGradeNotFoundForNameException
     * @return 404 NOT_FOUND - 예외 메시지 반환
     * @author woody35545(구건모)
     */
    @ExceptionHandler(MemberGradeNotFoundForNameException.class)
    public ResponseEntity<BaseResponse<Void>> handleMemberGradeNotFoundForNameException(MemberGradeNotFoundForNameException memberGradeNotFoundForNameException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(memberGradeNotFoundForNameException.getMessage()));
    }

    /**
     * 회원이 존재하지 않는 경우에 대한 예외 처리 핸들러
     * @param memberNotFoundException 회원이 존재하지 않는 경우 발생하는 예외
     * @see com.t3t.bookstoreapi.member.exception.MemberNotFoundException
     * @author woody35545(구건모)
     */
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleMemberNotFoundException(MemberNotFoundException memberNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(memberNotFoundException.getMessage()));
    }

    /**
     * 회원 주소가 존재하지 않는 경우에 대한 예외 처리 핸들러
     *
     * @author woody35545(구건모)
     * @see com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundException
     */
    @ExceptionHandler(MemberAddressNotFoundException.class)
    public ResponseEntity<BaseResponse<Void>> handleMemberAddressNotFoundException(MemberAddressNotFoundException memberAddressNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new BaseResponse<Void>().message(memberAddressNotFoundException.getMessage()));
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

    /**
     * ConstraintViolationException을 처리하는 핸들러
     * @param constraintViolationException 제약 조건 위반 예외 객체
     * @return 400 Bad Request 응답과 함께 에러 메시지를 포함한 응답 본문
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgumentException(ConstraintViolationException constraintViolationException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<Void>().message(constraintViolationException.getMessage()));
    }

    /**
     * MissingServletRequestParameterException을 처리하는 핸들러
     *
     * @param missingParamException 요청 파라미터 누락 예외 객체
     * @return 400 Bad Request 응답과 함께 에러 메시지를 포함한 응답 본문
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException missingParamException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<Void>().message(missingParamException.getMessage()));
    }
}