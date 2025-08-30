package com.tanpuh.kickshub.dto.request;

import com.tanpuh.kickshub.entity.ProductDetail;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemRequest {
//    Integer id; // not for creation, update

    @NotNull(message = "CART_ITEM_QTY_NULL")
    @PositiveOrZero(message = "CART_ITEM_QTY_NEGATIVE")
    Integer qty;

    ProductDetail productDetail;
    Integer cartId;
}
