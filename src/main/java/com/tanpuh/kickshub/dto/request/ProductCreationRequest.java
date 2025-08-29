package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    Integer id; // not for creation, update

    @NotBlank (message = "PRODUCT_CODE_BLANK")
    String code;

    @NotBlank (message = "PRODUCT_NAME_BLANK")
    String name;

    String description;

    @NotNull (message = "PRODUCT_CATEGORY_NULL")
    CategoryRequest category;
}
