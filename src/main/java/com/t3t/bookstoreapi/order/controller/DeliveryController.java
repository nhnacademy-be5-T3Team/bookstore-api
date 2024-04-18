package com.t3t.bookstoreapi.order.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.order.model.dto.DeliveryDto;
import com.t3t.bookstoreapi.order.model.request.DeliveryCreationRequest;
import com.t3t.bookstoreapi.order.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    /**
     * 모든 배송 목록을 조회하는 API
     *
     * @return 200 OK - 조회된 배송들에 대한 DTO 리스트 반환<br>
     * 204 NO_CONTENT - 등록된 배송이 없는 경우 메시지 반환
     * @author woody35545(구건모)
     */
    @GetMapping("/deliveries")
    public ResponseEntity<BaseResponse<List<DeliveryDto>>> getAllDeliveryList() {
        List<DeliveryDto> deliveryDtoList = deliveryService.getAllDeliveryList();

        return deliveryDtoList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<DeliveryDto>>().message("등록된 배송이 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<DeliveryDto>>().data(deliveryDtoList));
    }

    /**
     * 배송 식별자로 배송을 조회하는 API
     *
     * @param deliveryId 조회하고자 하는 배송 식별자
     * @return 200 OK - 조회된 배송에 대한 DTO 반환
     * @author woody35545(구건모)
     */
    @GetMapping("/deliveries/{deliveryId}")
    public ResponseEntity<BaseResponse<DeliveryDto>> getDeliveryById(@PathVariable("deliveryId") long deliveryId) {
        return ResponseEntity.ok(new BaseResponse<DeliveryDto>().data(deliveryService.getDeliveryById(deliveryId)));
    }

    /**
     * 배송을 생성하는 API
     * @param request 생성하고자 하는 배송에 대한 정보를 담은 DTO
     * @return 201 CREATED - 생성된 배송에 대한 DTO 반환
     * @author woody35545(구건모)
     */
    @PostMapping("/deliveries")
    public ResponseEntity<BaseResponse<DeliveryDto>> createDelivery(@Valid @RequestBody DeliveryCreationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<DeliveryDto>()
                .data(deliveryService.createDelivery(request)));
    }
}
