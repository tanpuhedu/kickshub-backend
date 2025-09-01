package com.tanpuh.kickshub.service.cart;

import com.tanpuh.kickshub.dto.request.CartItemRequest;
import com.tanpuh.kickshub.dto.response.CartItemResponse;
import com.tanpuh.kickshub.dto.response.CartResponse;

public interface CartItemService {
    CartResponse getCartByUsername(String username);
    CartItemResponse addToCart(CartItemRequest request);
    CartItemResponse updateQuantity(Integer id, Integer quantity);
    void delete(Integer id);
}
