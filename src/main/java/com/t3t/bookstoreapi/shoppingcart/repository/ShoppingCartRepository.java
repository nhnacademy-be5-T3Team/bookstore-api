package com.t3t.bookstoreapi.shoppingcart.repository;

import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{
}
