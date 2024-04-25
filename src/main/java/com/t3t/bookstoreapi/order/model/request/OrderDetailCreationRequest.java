package com.t3t.bookstoreapi.order.model.request;

import com.t3t.bookstoreapi.order.model.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 주문 상세 생성 요청 객체
 *
 * @auhtor woody35545(구건모)
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailCreationRequest {
    /**
     * 주문 상세가 속한 주문 정보
     */
    @NotNull(message = "주문 식별자가 누락되었습니다.")
    private Long orderId;

    @NotNull(message = "주문 상태가 누락되었습니다.")
    private OrderStatus orderStatus;

    /**
     * 상품 및 수량 정보
     */
    @NotNull(message = "상품 식별자가 누락되었습니다.")
    private Long bookId;

    @NotNull(message = "수량이 누락되었습니다.")
    @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
    private Long quantity;

    /**
     * 주문 상세 단건 가격<br>
     * 주문 요청 시점의 책 가격과 할인율 등 결제 금액에 영향을 주는 요인들을 고려해서 산출된 금액으로<br>
     * 최종적으로 사용자가 결제해야하는 상품 단건 가격을 의미한다.
     */
    @NotNull(message = "주문 상세에 대한 가격이 누락되었습니다.")
    private BigDecimal price;

    /**
     * 포장 정보<br>
     * 포장이 있는 경우 포장 식별자, 포장이 없는 경우 null 로 설정한다.
     */
    @Nullable
    private Long packagingId;

}
