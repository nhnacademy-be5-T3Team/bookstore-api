package com.t3t.bookstoreapi.shoppingcart.repository;

import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartDetailRepository extends JpaRepository<ShoppingCartDetail, Long>{
    List<ShoppingCartDetail> findAllByShoppingCartId(Long shoppingCartId);
}
