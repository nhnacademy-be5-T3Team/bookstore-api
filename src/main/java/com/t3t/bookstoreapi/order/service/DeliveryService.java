package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.order.exception.DeliveryNotFoundForIdException;
import com.t3t.bookstoreapi.order.model.dto.DeliveryDto;
import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.model.request.DeliveryCreationRequest;
import com.t3t.bookstoreapi.order.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    /**
     * 전체 배송 목록을 조회한다.
     * @return 조회된 배송 목록에 대한 DTO 리스트
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public List<DeliveryDto> getAllDeliveryList() {
        return deliveryRepository.findAll().stream()
                .map(DeliveryDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 배송 식별자로 배송을 조회한다.
     * @param deliveryId 조회하고자 하는 배송 식별자
     * @return 조회된 배송에 대한 DTO
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public DeliveryDto getDeliveryById(Long deliveryId) {
        return DeliveryDto.of(deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new DeliveryNotFoundForIdException(deliveryId)));
    }

    /**
     * 배송을 생성한다.
     * @param request 생성하고자 하는 배송에 대한 정보를 담은 DTO<br>
     * - price 배송비<br>
     * - addressNumber 배송 우편 주소<br>
     * - roadnameAddress 배송 도로명 주소<br>
     * - detailAddress 배송 상세 주소<br>
     * - recipientName 배송 수령인 이름<br>
     * - recipientPhoneNumber 배송 수령인 전화번호<br>
     * @return 생성된 배송에 대한 DTO
     * @author woody35545(구건모)
     */
    public DeliveryDto createDelivery(DeliveryCreationRequest request) {
        return DeliveryDto.of(deliveryRepository.save(Delivery.builder()
                .price(request.getPrice())
                .addressNumber(request.getAddressNumber())
                .roadnameAddress(request.getRoadnameAddress())
                .detailAddress(request.getDetailAddress())
                .recipientName(request.getRecipientName())
                .recipientPhoneNumber(request.getRecipientPhoneNumber())
                .deliveryDate(request.getDeliveryDate())
                .build()));
    }
}
