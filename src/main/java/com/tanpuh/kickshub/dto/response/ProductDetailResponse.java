package com.tanpuh.kickshub.dto.response;

import com.tanpuh.kickshub.utils.enums.EntityStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    Integer id;
    String code;
    String name;
    Long price;
    Integer stockQty;
    Integer soldQty;
    EntityStatus status;

    ProductResponse product;
    ColorResponse color;
    SizeResponse size;
}
