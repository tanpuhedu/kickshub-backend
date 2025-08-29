package com.tanpuh.kickshub.dto.request;

import com.tanpuh.kickshub.utils.validators.StatusConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdateRequest {
    Integer id; // not for creation, update

    @NotBlank (message = "PRODUCT_NAME_BLANK")
    String name;

    String description;

    @NotNull (message = "PRODUCT_STATUS_NULL")
    @StatusConstraint (min = 0, max = 1, message = "PRODUCT_STATUS_INVALID")
    Integer status;

    @NotNull (message = "PRODUCT_CATEGORY_NULL")
    CategoryRequest category;
}
