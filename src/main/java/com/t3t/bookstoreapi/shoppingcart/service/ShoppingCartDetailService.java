package com.t3t.bookstoreapi.shoppingcart.service;

import com.t3t.bookstoreapi.shoppingcart.exception.ShoppingCartNotFoundForIdException;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCartDetail;
import com.t3t.bookstoreapi.shoppingcart.repository.ShoppingCartDetailRepository;
import com.t3t.bookstoreapi.shoppingcart.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShoppingCartDetailService {

    private final ShoppingCartDetailRepository shoppingCartDetailRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    /**
     * 장바구니 식별자로 전체 항목 리스트 조회
     * @param shoppingCartId 조회하려는 장바구니 식별자
     * @return 장바구니 식별자에 해당하는 전체 항목 리스트
     * @author wooody35545(구건모)
     */
    @Transactional(readOnly = true)
    public List<ShoppingCartDetail> getShoppingCartDetailList(long shoppingCartId) {

        if (!shoppingCartRepository.existsById(shoppingCartId)) {
            throw new ShoppingCartNotFoundForIdException(shoppingCartId);
        }

        return shoppingCartDetailRepository.findAllByShoppingCartId(shoppingCartId);
    }
}
