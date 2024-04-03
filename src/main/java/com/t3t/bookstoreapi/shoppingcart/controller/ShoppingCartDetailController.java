package com.t3t.bookstoreapi.shoppingcart.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCartDetail;
import com.t3t.bookstoreapi.shoppingcart.service.ShoppingCartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShoppingCartDetailController {

    private final ShoppingCartDetailService shoppingCartDetailService;

    /**
     * 장바구니 식별자로 전체 항목 리스트 조회
     * @param shoppingCartId 조회하려는 장바구니 식별자
     * @return 장바구니 식별자에 해당하는 전체 항목 리스트
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
}
