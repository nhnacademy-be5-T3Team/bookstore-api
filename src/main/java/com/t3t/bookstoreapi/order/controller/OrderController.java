package com.t3t.bookstoreapi.order.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.model.request.MemberOrderPreparationRequest;
import com.t3t.bookstoreapi.order.model.request.OrderConfirmRequest;
import com.t3t.bookstoreapi.order.model.request.GuestOrderPreparationRequest;
import com.t3t.bookstoreapi.order.model.response.GuestOrderPreparationResponse;
import com.t3t.bookstoreapi.order.model.response.MemberOrderPreparationResponse;
import com.t3t.bookstoreapi.order.model.response.OrderDetailInfoResponse;
import com.t3t.bookstoreapi.order.model.response.OrderInfoResponse;
import com.t3t.bookstoreapi.order.service.OrderDetailService;
import com.t3t.bookstoreapi.order.service.OrderService;
import com.t3t.bookstoreapi.order.service.OrderServiceFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderDetailService orderDetailService;
    private final OrderServiceFacade orderServiceFacade;
    private final OrderService orderService;

    /**
     * 주문 내에 속해있는 주문 상세 리스트 조회
     *
     * @param orderId 조회하려는 주문 식별자
     * @author woody35545(구건모)
     */
    @GetMapping("/orders/{orderId}/details")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<OrderDetailInfoResponse>> getOrderDetailDtoListByOrderId(@PathVariable("orderId") long orderId) {
        return new BaseResponse<List<OrderDetailInfoResponse>>().data(orderDetailService.getOrderDetailInfoResponse(orderId));
    }

    /**
     * 주문 승인
     *
     * @param orderConfirmRequest 주문 승인 요청 정보
     * @author woody35545(구건모)
     */
    @PostMapping("/orders/confirm")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Void> confirmOrder(@RequestBody OrderConfirmRequest orderConfirmRequest) {
        log.info("[*] orderConfirmRequest => {}", orderConfirmRequest);
        orderServiceFacade.confirmOrder(orderConfirmRequest);
        return new BaseResponse<Void>().message("주문 승인이 완료되었습니다.");
    }

    /**
     * 비회원 주문 생성 API
     * 주문은 기본적으로 결제 대기 상태로 생성된다.
     * 결제 완료 후 주문 승인 요청을 통해 주문이 승인 처리된다.
     *
     * @param request 주문 생성 요청 정보
     * @return 201 CREATED - 주문 생성 성공
     * @author woody35545(구건모)
     */
    @PostMapping("/orders/guest")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<GuestOrderPreparationResponse> createMemberOrder(@RequestBody GuestOrderPreparationRequest request) {
        return new BaseResponse<GuestOrderPreparationResponse>().data(orderServiceFacade.prepareOrder(request));
    }

    /**
     * 회원 주문 생성 API
     * 주문은 기본적으로 결제 대기 상태로 생성된다.
     * 결제 완료 후 주문 승인 요청을 통해 주문이 승인 처리된다.
     *
     * @param request 주문 생성 요청 정보
     * @return 201 CREATED - 주문 생성 성공
     * @author woody35545(구건모)
     */
    @PostMapping("/orders/member")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<MemberOrderPreparationResponse> createMemberOrder(@RequestBody MemberOrderPreparationRequest request) {
        return new BaseResponse<MemberOrderPreparationResponse>().data(orderServiceFacade.prepareOrder(request));
    }

    /**
     * 특정 회원의 모든 주문 관련 정보를 페이징을 통해 조회
     *
     * @author woody35545(구건모)
     */
    @GetMapping("/members/{memberId}/orders")
    public BaseResponse<Page<OrderInfoResponse>> getMemberOrderInfoListByMemberId(@PathVariable("memberId") Long memberId, Pageable pageable) {
        return new BaseResponse<Page<OrderInfoResponse>>().data(orderService.getMemberOrderInfoListByMemberId(memberId, pageable));
    }
}
