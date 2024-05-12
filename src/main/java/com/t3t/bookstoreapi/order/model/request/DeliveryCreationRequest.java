package com.t3t.bookstoreapi.order.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 배송 생성에 사용되는 요청 객체
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryCreationRequest {
    // 배송비
    @NotNull(message = "배송비가 누락되었습니다.")
    @DecimalMin(value = "0.0", message = "배송비는 0보다 크거나 같아야 합니다.")
    private BigDecimal price;

    // 배송 우편 주소
    @Nullable
    private Integer addressNumber;

    // 배송 도로명 주소
    @Nullable
    private String roadnameAddress;

    // 배송 상세 주소
    @NotBlank(message = "상세 주소가 누락되었습니다.")
    private String detailAddress;

    // 희망 배송 일자
    @NotNull(message = "희망 배송 일자가 누락되었습니다.")
    @Future(message = "희망 배송 일자는 현재 날짜보다 이후 날짜여야 합니다.")
    private LocalDate deliveryDate;

    // 배송 수령인 이름
    @NotBlank(message = "수령인 이름이 누락되었습니다.")
    private String recipientName;

    // 배송 수령인 전화번호
    @NotBlank(message = "수령인 전화번호가 누락되었습니다.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "올바른 전화번호 형식이 아닙니다. (예시: 010-1234-5678)")
    private String recipientPhoneNumber;

    @AssertTrue(message = "우편 주소와 도로명 주소 중 하나는 반드시 입력되어야 합니다.")
    private boolean isEitherAddressNotNull() {
        return addressNumber != null || roadnameAddress != null;
    }
}
