package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailCreationRequest {
    Integer id; // not for creation, update

    @NotBlank(message = "PRODUCT_DETAIL_CODE_BLANK")
    String code;

    @NotBlank (message = "PRODUCT_DETAIL_NAME_BLANK")
    String name;

    @NotNull (message = "PRODUCT_DETAIL_PRICE_NULL")
    @Positive (message = "PRODUCT_DETAIL_PRICE_NEGATIVE_OR_ZERO")
    Long price;

    @NotNull (message = "PRODUCT_DETAIL_STOCKQTY_NULL")
    @PositiveOrZero (message = "PRODUCT_DETAIL_STOCKQTY_NEGATIVE")
    Integer stockQty;

    @NotNull (message = "PRODUCT_DETAIL_PRODUCT_NULL")
    ProductCreationRequest product;

    @NotNull (message = "PRODUCT_DETAIL_COLOR_NULL")
    ColorRequest color;

    @NotNull (message = "PRODUCT_DETAIL_SIZE_NULL")
    SizeRequest size;
}
