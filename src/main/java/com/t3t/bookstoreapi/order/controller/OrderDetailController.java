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

@RestController
@RequiredArgsConstructor
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    /**
     * 주문 상세 조회 - 주문 상세 식별자로 조회
     *
     * @param orderDetailId 주문 상세 식별자
     * @author woody35545(구건모)
     */
    @GetMapping("/order-details/{orderDetailId}")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<OrderDetailDto> getOrderDetailDtoById(@PathVariable("orderDetailId") long orderDetailId) {

        return new BaseResponse<OrderDetailDto>().data(orderDetailService.getOrderDetailDtoById(orderDetailId));
    }
}
