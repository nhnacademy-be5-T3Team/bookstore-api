package com.t3t.bookstoreapi.order.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.model.dto.OrderStatusDto;
import com.t3t.bookstoreapi.order.service.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    /**
     * 모든 주문 상태를 조회하는 API
     *
     * @return 조회된 주문 상태들에 대한 DTO 리스트
     * @author woody35545(구건모)
     */
    @GetMapping("/orders/status")
    public ResponseEntity<BaseResponse<List<OrderStatusDto>>> getAllOrderStatusList() {

        List<OrderStatusDto> orderStatusDtoList = orderStatusService.getAllOrderStatusList();

        BaseResponse<List<OrderStatusDto>> responseBody = new BaseResponse<>();

        return orderStatusDtoList.isEmpty() ?
                ResponseEntity.ok(responseBody.message("등록된 주문 상태가 없습니다.")) :
                ResponseEntity.status(HttpStatus.OK).body(responseBody.data(orderStatusDtoList));
    }
}
