package com.t3t.bookstoreapi.order.servcie;

import com.t3t.bookstoreapi.order.exception.DeliveryNotFoundForIdException;
import com.t3t.bookstoreapi.order.model.dto.DeliveryDto;
import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.repository.DeliveryRepository;
import com.t3t.bookstoreapi.order.service.DeliveryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceUnitTest {
    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    /**
     * 배송 조회 - 전체 조회
     * @see DeliveryService#getAllDeliveryList()
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("배송 조회 - 전체 조회")
    void getAllDeliveryListTest() {
        // given
        List<Delivery> testDeliveryList = List.of(
                Delivery.builder().id(0L).deliveryDate(LocalDate.now()).price(BigDecimal.valueOf(10000)).addressNumber(12345).roadnameAddress("testRoadnameAddress0").detailAddress("testDetailAddress0").recipientName("testRecipientName0").recipientPhoneNumber("testRecipientPhoneNumber0").build(),
                Delivery.builder().id(1L).deliveryDate(LocalDate.now()).price(BigDecimal.valueOf(20000)).addressNumber(12346).roadnameAddress("testRoadnameAddress1").detailAddress("testDetailAddress1").recipientName("testRecipientName1").recipientPhoneNumber("testRecipientPhoneNumber1").build(),
                Delivery.builder().id(2L).deliveryDate(LocalDate.now()).price(BigDecimal.valueOf(30000)).addressNumber(12347).roadnameAddress("testRoadnameAddress2").detailAddress("testDetailAddress2").recipientName("testRecipientName2").recipientPhoneNumber("testRecipientPhoneNumber2").build()
        );

        Mockito.doReturn(testDeliveryList).when(deliveryRepository).findAll();

        // when
        List<DeliveryDto> resultDeliveryDtoList = deliveryService.getAllDeliveryList();

        // then
        Assertions.assertEquals(testDeliveryList.size(), resultDeliveryDtoList.size());

        for (int i = 0; i < testDeliveryList.size(); i++) {
            Assertions.assertEquals(testDeliveryList.get(i).getId(), resultDeliveryDtoList.get(i).getId());
            Assertions.assertEquals(testDeliveryList.get(i).getPrice(), resultDeliveryDtoList.get(i).getPrice());
            Assertions.assertEquals(testDeliveryList.get(i).getAddressNumber(), resultDeliveryDtoList.get(i).getAddressNumber());
            Assertions.assertEquals(testDeliveryList.get(i).getRoadnameAddress(), resultDeliveryDtoList.get(i).getRoadnameAddress());
            Assertions.assertEquals(testDeliveryList.get(i).getDetailAddress(), resultDeliveryDtoList.get(i).getDetailAddress());
            Assertions.assertEquals(testDeliveryList.get(i).getDeliveryDate(), resultDeliveryDtoList.get(i).getDeliveryDate());
            Assertions.assertEquals(testDeliveryList.get(i).getRecipientName(), resultDeliveryDtoList.get(i).getRecipientName());
            Assertions.assertEquals(testDeliveryList.get(i).getRecipientPhoneNumber(), resultDeliveryDtoList.get(i).getRecipientPhoneNumber());
        }
    }

    /**
     * 배송 조회 - 식별자로 조회
     * @see DeliveryService#getDeliveryById(Long)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("배송 조회 - 식별자로 조회")
    void getDeliveryByIdTest() {
        // given
        Delivery testDelivery = Delivery.builder().id(0L).deliveryDate(LocalDate.now()).price(BigDecimal.valueOf(10000)).addressNumber(12345).roadnameAddress("testRoadnameAddress0").detailAddress("testDetailAddress0").recipientName("testRecipientName0").recipientPhoneNumber("testRecipientPhoneNumber0").build();

        Mockito.doReturn(java.util.Optional.of(testDelivery)).when(deliveryRepository).findById(testDelivery.getId());

        // when
        DeliveryDto resultDeliveryDto = deliveryService.getDeliveryById(testDelivery.getId());

        // then
        Assertions.assertEquals(testDelivery.getId(), resultDeliveryDto.getId());
        Assertions.assertEquals(testDelivery.getPrice(), resultDeliveryDto.getPrice());
        Assertions.assertEquals(testDelivery.getAddressNumber(), resultDeliveryDto.getAddressNumber());
        Assertions.assertEquals(testDelivery.getRoadnameAddress(), resultDeliveryDto.getRoadnameAddress());
        Assertions.assertEquals(testDelivery.getDetailAddress(), resultDeliveryDto.getDetailAddress());
        Assertions.assertEquals(testDelivery.getDeliveryDate(), resultDeliveryDto.getDeliveryDate());
        Assertions.assertEquals(testDelivery.getRecipientName(), resultDeliveryDto.getRecipientName());
        Assertions.assertEquals(testDelivery.getRecipientPhoneNumber(), resultDeliveryDto.getRecipientPhoneNumber());
    }

    /**
     * 배송 조회 - 식별자로 조회(존재하지 않는 배송)
     * @apiNote 요청한 배송 식별자에 대한 배송이 존재하지 않는 경우 `DeliveryNotFoundForIdException` 가 발생해야 한다.
     * @author woody35545(구건모)
     * @see DeliveryService#getDeliveryById(Long)
     * @see com.t3t.bookstoreapi.order.exception.DeliveryNotFoundForIdException
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("배송 조회 - 식별자로 조회(존재하지 않는 배송)")
    void getDeliveryByIdTest_NotFound() {

        long testDeliveryId = 0L;
        // given
        Mockito.doReturn(Optional.empty()).when(deliveryRepository).findById(testDeliveryId);

        // when & then
        Assertions.assertThrows(DeliveryNotFoundForIdException.class, () -> deliveryService.getDeliveryById(testDeliveryId));
    }

    /**
     * 배송 생성
     * @see DeliveryService#createDelivery(BigDecimal, int, String, String, LocalDate, String, String)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("배송 생성")
    void createDeliveryTest() {
        // given
        BigDecimal testPrice = BigDecimal.valueOf(10000);
        int testAddressNumber = 12345;
        String testRoadnameAddress = "testRoadnameAddress";
        String testDetailAddress = "testDetailAddress";
        String testRecipientName = "testRecipientName";
        String testRecipientPhoneNumber = "testRecipientPhoneNumber";
        LocalDate testDeliveryDate = LocalDate.now();

        Delivery testDelivery = Delivery.builder()
                .price(testPrice)
                .addressNumber(testAddressNumber)
                .roadnameAddress(testRoadnameAddress)
                .detailAddress(testDetailAddress)
                .recipientName(testRecipientName)
                .recipientPhoneNumber(testRecipientPhoneNumber)
                .deliveryDate(testDeliveryDate)
                .build();

        Mockito.when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(testDelivery);

        // when
        DeliveryDto resultDeliveryDto = deliveryService.createDelivery(testPrice, testAddressNumber, testRoadnameAddress, testDetailAddress, testDeliveryDate, testRecipientName, testRecipientPhoneNumber);

        // then
        Assertions.assertEquals(testPrice, resultDeliveryDto.getPrice());
        Assertions.assertEquals(testAddressNumber, resultDeliveryDto.getAddressNumber());
        Assertions.assertEquals(testRoadnameAddress, resultDeliveryDto.getRoadnameAddress());
        Assertions.assertEquals(testDetailAddress, resultDeliveryDto.getDetailAddress());
        Assertions.assertEquals(testDeliveryDate, resultDeliveryDto.getDeliveryDate());
        Assertions.assertEquals(testRecipientName, resultDeliveryDto.getRecipientName());
        Assertions.assertEquals(testRecipientPhoneNumber, resultDeliveryDto.getRecipientPhoneNumber());
    }
}
