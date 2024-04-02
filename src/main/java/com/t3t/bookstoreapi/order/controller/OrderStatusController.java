package com.t3t.bookstoreapi.order.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.model.dto.OrderStatusDto;
import com.t3t.bookstoreapi.order.service.OrderStatusService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    /**
     * 모든 주문 상태를 조회하는 API
     *
     * @return 200 OK - 조회된 주문 상태들에 대한 DTO 리스트 반환<br>
     * 204 NO_CONTENT - 등록된 주문 상태가 없는 경우 메시지 반환
     * @author woody35545(구건모)
     */
    @GetMapping("/orders/status")
    public ResponseEntity<BaseResponse<List<OrderStatusDto>>> getAllOrderStatusList() {

        List<OrderStatusDto> orderStatusDtoList = orderStatusService.getAllOrderStatusList();

        BaseResponse<List<OrderStatusDto>> responseBody = new BaseResponse<>();

        return orderStatusDtoList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBody.message("등록된 주문 상태가 없습니다.")) :
                ResponseEntity.ok(responseBody.data(orderStatusDtoList));
    }

    /**
     * 주문 상태 식별자로 주문 상태를 조회하는 API
     * @param orderStatusId 조회하고자 하는 주문 상태 식별자
     * @return 200 OK - 조회된 주문 상태에 대한 DTO 반환
     * @author woody35545(구건모)
     */
    @GetMapping("/orders/status/{orderStatusId}")
    public ResponseEntity<BaseResponse<OrderStatusDto>> getOrderStatusById(@PathVariable long orderStatusId) {

        return ResponseEntity.ok(new BaseResponse<OrderStatusDto>()
                .data(orderStatusService.getOrderStatusById(orderStatusId)));
    }

    /**
     * 주문 상태 이름으로 주문 상태를 조회하는 API
     * @param statusName 조회하고자 하는 주문 상태 이름
     * @return 200 OK - 조회된 주문 상태에 대한 DTO 반환
     * @author woody35545(구건모)
     */
    @GetMapping("/orders/status/{statusName}")
    public ResponseEntity<BaseResponse<OrderStatusDto>> getOrderStatusByName(@PathVariable String statusName) {

        return ResponseEntity.ok(new BaseResponse<OrderStatusDto>()
                .data(orderStatusService.getOrderStatusByName(statusName)));
    }
}
