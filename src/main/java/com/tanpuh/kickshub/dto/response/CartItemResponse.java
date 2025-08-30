package com.tanpuh.kickshub.dto.response;

import com.tanpuh.kickshub.entity.Cart;
import com.tanpuh.kickshub.entity.ProductDetail;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Integer id; // not for creation, update
    Integer qty;
    ProductDetail productDetail;
    Cart cart;
}
