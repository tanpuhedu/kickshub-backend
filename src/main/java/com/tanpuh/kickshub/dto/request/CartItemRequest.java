package com.tanpuh.kickshub.dto.request;

import com.tanpuh.kickshub.entity.ProductDetail;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
//    Integer id; // not for creation, update
    Integer qty;

    ProductDetail productDetail;
    Integer cartId;
}
