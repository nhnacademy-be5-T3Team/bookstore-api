package com.t3t.bookstoreapi.shoppingcart.service;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.shoppingcart.exception.ShoppingCartNotFoundForIdException;
import com.t3t.bookstoreapi.shoppingcart.model.dto.ShoppingCartDetailDto;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCart;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCartDetail;
import com.t3t.bookstoreapi.shoppingcart.repository.ShoppingCartDetailRepository;
import com.t3t.bookstoreapi.shoppingcart.repository.ShoppingCartRepository;
import com.t3t.bookstoreapi.shoppingcart.service.ShoppingCartDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 장바구니 항목 서비스 단위 테스트
 * @see ShoppingCartDetailService
 */
@ExtendWith(MockitoExtension.class)
public class ShoppingCartDetailServiceUnitTest {
    @Mock
    ShoppingCartRepository shoppingCartRepository;
    @Mock
    ShoppingCartDetailRepository shoppingCartDetailRepository;

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    ShoppingCartDetailService shoppingCartService;

    /**
     * 장바구니 식별자로 전체 항목 리스트 조회
     * @author wooody355(구건모)
     */
    @Test
    @DisplayName("장바구니 식별자로 전체 항목 리스트 조회")
    void getShoppingCartDetailList() {

        // given
        ShoppingCart testShoppingCart = ShoppingCart.builder()
                .id(0L)
                .build();

        List<Book> testBookList = List.of(
                Book.builder().bookId(0L).build(),
                Book.builder().bookId(1L).build()
        );

        List<ShoppingCartDetail> shoppingCartDetailList = new ArrayList<>();

        for (int i = 0; i < testBookList.size(); i++) {
            shoppingCartDetailList.add(ShoppingCartDetail.builder()
                    .id((long) i)
                    .book(testBookList.get(0))
                    .shoppingCart(testShoppingCart)
                    .quantity(1L)
                    .build()
            );
        }

        Mockito.when(shoppingCartRepository.existsById(testShoppingCart.getId())).thenReturn(true);
        Mockito.when(shoppingCartDetailRepository.findAllByShoppingCartId(testShoppingCart.getId())).thenReturn(shoppingCartDetailList);

        // when
        List<ShoppingCartDetail> resultShoppingCartDetailList
                = shoppingCartService.getShoppingCartDetailList(testShoppingCart.getId());

        // then
        Assertions.assertFalse(resultShoppingCartDetailList.isEmpty());
        Assertions.assertEquals(shoppingCartDetailList.size(), resultShoppingCartDetailList.size());

        for(int i = 0; i < shoppingCartDetailList.size(); i++) {
            Assertions.assertEquals(shoppingCartDetailList.get(i).getId(), resultShoppingCartDetailList.get(i).getId());
            Assertions.assertEquals(shoppingCartDetailList.get(i).getBook(), resultShoppingCartDetailList.get(i).getBook());
            Assertions.assertEquals(shoppingCartDetailList.get(i).getShoppingCart(), resultShoppingCartDetailList.get(i).getShoppingCart());
            Assertions.assertEquals(shoppingCartDetailList.get(i).getQuantity(), resultShoppingCartDetailList.get(i).getQuantity());
        }
    }

    /**
     * 존재하지 않는 장바구니 식별자로 항목을 조회할 때 발생하면 ShoppingCartNotFoundForIdException 가 발생해야한다.
     * @throws ShoppingCartNotFoundForIdException 장바구니 식별자가 존재하지 않을 때 발생하는 예외
     * @author wooody35545(구건모)
     */
    @Test
    @DisplayName("장바구니 항목 조회 - 존재하지 않는 장바구니 식별자")
    void getShoppingCartDetailList_ShoppingCartNotFoundForIdException() {
        // given
        long testShoppingCartId = 0L;
        Mockito.when(shoppingCartRepository.existsById(testShoppingCartId)).thenReturn(false);

        // when & then
        Assertions.assertThrows(ShoppingCartNotFoundForIdException.class,
                () -> shoppingCartService.getShoppingCartDetailList(testShoppingCartId));
    }

    /**
     * 장바구니에 항목 추가
     * @see ShoppingCartDetailService#createShoppingCartDetail
     * @author wooody355(구건모)
     */
    @Test
    @DisplayName("장바구니 항목 생성")
    void createShoppingCartDetail() {
        // given
        long testShoppingCartId = 0L;
        long testBookId = 0L;
        long testQuantity = 1L;

        ShoppingCart testShoppingCart = ShoppingCart.builder()
                .id(testShoppingCartId)
                .build();

        Book testBook = Book.builder()
                .bookId(testBookId)
                .build();

        ShoppingCartDetail testShoppingCartDetail = ShoppingCartDetail.builder()
                .id(0L)
                .shoppingCart(testShoppingCart)
                .book(testBook)
                .quantity(testQuantity)
                .build();

        Mockito.when(shoppingCartRepository.findById(testShoppingCartId)).thenReturn(Optional.of(testShoppingCart));
        Mockito.when(bookRepository.findById(testBookId)).thenReturn(Optional.of(testBook));
        Mockito.when(shoppingCartDetailRepository.save(Mockito.any(ShoppingCartDetail.class))).thenReturn(testShoppingCartDetail);

        // when
        ShoppingCartDetailDto resultShoppingCartDetailDto = shoppingCartService.createShoppingCartDetail(testShoppingCartId, testBookId, testQuantity);

        // then
        Assertions.assertEquals(testShoppingCartDetail.getId(), resultShoppingCartDetailDto.getId());
        Assertions.assertEquals(testShoppingCartDetail.getShoppingCart(), resultShoppingCartDetailDto.getShoppingCart());
        Assertions.assertEquals(testShoppingCartDetail.getBook(), resultShoppingCartDetailDto.getBook());
        Assertions.assertEquals(testShoppingCartDetail.getQuantity(), resultShoppingCartDetailDto.getQuantity());
    }

    /**
     * 장바구니에 항목 추가 - 요청 파라미터의 수량이 0 이하일 때 IllegalArgumentException 이 발생해야한다.
     * @see ShoppingCartDetailService#createShoppingCartDetail
     * @author wooody355(구건모)
     */
    @Test
    @DisplayName("장바구니 항목 추가 - 요청 파라미터의 수량이 0 이하일 때")
    void createShoppingCartDetailWithQuantityLessThanZero() {
        // given
        long testShoppingCartId = 0L;
        long testBookId = 0L;
        long testQuantity = 0L;

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> shoppingCartService.createShoppingCartDetail(testShoppingCartId, testBookId, testQuantity));
    }
}
