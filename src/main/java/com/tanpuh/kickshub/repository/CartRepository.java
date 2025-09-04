package com.tanpuh.kickshub.repository;

import com.tanpuh.kickshub.entity.Cart;
import com.tanpuh.kickshub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUser(User user);
}
