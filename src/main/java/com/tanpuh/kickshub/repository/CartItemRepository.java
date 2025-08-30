package com.tanpuh.kickshub.repository;

import com.tanpuh.kickshub.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByCartIdAndProductDetailId(Integer cartId, Integer productDetailId);
}
