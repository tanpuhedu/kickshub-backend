package com.tanpuh.kickshub.repository;

import com.tanpuh.kickshub.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
