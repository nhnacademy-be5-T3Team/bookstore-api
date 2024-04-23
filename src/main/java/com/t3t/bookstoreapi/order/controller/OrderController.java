package com.t3t.bookstoreapi.order.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderDetailService orderDetailService;

    /**
     * 주문 내에 속해있는 주문 상세 리스트 조회
     * @param orderId 조회하려는 주문 식별자
     * @author woody35545(구건모)
     */
    @GetMapping("/orders/{orderId}/order-details")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<OrderDetailDto>> getOrderDetailDtoListByOrderId(@PathVariable("orderId") long orderId) {

        return new BaseResponse<List<OrderDetailDto>>().data(orderDetailService.getOrderDetailDtoListByOrderId(orderId));
    }
}
