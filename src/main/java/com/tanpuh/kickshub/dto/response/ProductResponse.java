package com.tanpuh.kickshub.dto.response;

import com.tanpuh.kickshub.utils.enums.EntityStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Integer id;
    String code;
    String name;
    Long price;
    Integer stockQty;
    Integer soldQty;
    String description;
    EntityStatus status;

    CategoryResponse category;
}
