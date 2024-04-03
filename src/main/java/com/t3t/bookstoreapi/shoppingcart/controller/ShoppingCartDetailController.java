package com.t3t.bookstoreapi.shoppingcart.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.shoppingcart.model.dto.ShoppingCartDetailDto;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCartDetail;
import com.t3t.bookstoreapi.shoppingcart.model.request.ShoppingCartDetailCreationRequest;
import com.t3t.bookstoreapi.shoppingcart.service.ShoppingCartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShoppingCartDetailController {

    private final ShoppingCartDetailService shoppingCartDetailService;

    /**
     * 장바구니 식별자로 전체 항목 리스트 조회
     * @param shoppingCartId 조회하려는 장바구니 식별자
     * @return 200 OK, 장바구니 식별자에 해당하는 전체 항목 리스트<br>
     *         204 No Content, 장바구니에 담긴 상품이 없는 경우 메시지 반환
     * @author wooody35545(구건모)
     */
    @GetMapping("/shoppingcarts/{shoppingCartId}/details")
    public ResponseEntity<BaseResponse<List<ShoppingCartDetail>>> getShoppingCartDetailList(@PathVariable("shoppingCartId") long shoppingCartId) {
        List<ShoppingCartDetail> shoppingCartDetailList =
                shoppingCartDetailService.getShoppingCartDetailList(shoppingCartId);

        return shoppingCartDetailList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).
                        body(new BaseResponse<List<ShoppingCartDetail>>().message("장바구니에 담긴 상품이 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<ShoppingCartDetail>>()
                        .data(shoppingCartDetailList));
    }

    /**
     * 장바구니에 항목 추가
     * @param shoppingCartId 장바구니 식별자
     * @param request        추가할 장바구니 항목 정보
     * @return 200 OK, 추가된 장바구니 항목에 대한 DTO
     * @author wooody35545(구건모)
     */
    @PostMapping("/shoppingcarts/{shoppingCartId}/details")
    public ResponseEntity<BaseResponse<ShoppingCartDetailDto>> createShoppingCartDetail(@PathVariable("shoppingCartId") long shoppingCartId,
                                                                                        @Valid @RequestBody ShoppingCartDetailCreationRequest request) {
        return ResponseEntity.ok(new BaseResponse<ShoppingCartDetailDto>()
                .data(shoppingCartDetailService.createShoppingCartDetail(shoppingCartId, request)));
    }

    /**
     * 장바구니 항목 수량 변경
     * @param shoppingCartDetailId 변경하려는 장바구니 항목 식별자
     * @param quantity             변경할 수량
     * @return 200 OK, 변경된 장바구니 항목에 대한 DTO
     * @author wooody35545(구건모)
     */
    @PutMapping(value = "/shoppingcart-details/{shoppingCartDetailId}", params = "quantity")
    public ResponseEntity<BaseResponse<ShoppingCartDetailDto>> updateShoppingCartDetailQuantity(@PathVariable("shoppingCartDetailId") long shoppingCartDetailId,
                                                                                                @RequestParam("quantity") @Min(1) long quantity) {
        return ResponseEntity.ok(new BaseResponse<ShoppingCartDetailDto>()
                .data(shoppingCartDetailService.updateShoppingCartDetailQuantity(shoppingCartDetailId, quantity)));
    }

    /**
     * 장바구니 항목 삭제
     * @param shoppingCartDetailId 삭제하려는 장바구니 항목 식별자
     * @return 200 OK
     * @author wooody35545(구건모)
     */
    @DeleteMapping("/shoppingcart-details/{shoppingCartDetailId}")
    public ResponseEntity<BaseResponse<Void>> deleteShoppingCartDetail(@PathVariable("shoppingCartDetailId") long shoppingCartDetailId) {
        shoppingCartDetailService.deleteShoppingCartDetail(shoppingCartDetailId);
        return ResponseEntity.ok(new BaseResponse<Void>());
    }
}
