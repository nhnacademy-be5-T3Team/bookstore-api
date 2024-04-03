package com.t3t.bookstoreapi.shoppingcart.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.shoppingcart.exception.ShoppingCartNotFoundForIdException;
import com.t3t.bookstoreapi.shoppingcart.model.dto.ShoppingCartDetailDto;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCart;
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
    private final BookRepository bookRepository;

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

    /**
     * 장바구니에 항목 추가
     * @param shoppingCartId 장바구니 식별자
     * @param bookId 추가할 책 식별자
     * @param quantity 추가할 수량
     * @return 추가된 장바구니 항목에 대한 DTO
     * @author wooody35545(구건모)
     */
    public ShoppingCartDetailDto createShoppingCartDetail(long shoppingCartId, long bookId, long quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        }

        return ShoppingCartDetailDto.of(shoppingCartDetailRepository.save(ShoppingCartDetail.builder()
                .shoppingCart(shoppingCartRepository.findById(shoppingCartId)
                        .orElseThrow(() -> new ShoppingCartNotFoundForIdException(shoppingCartId)))
                .book(bookRepository.findById(bookId)
                        .orElseThrow(() -> new BookNotFoundForIdException(bookId)))
                .quantity(quantity)
                .build()));
    }
}
