package com.tanpuh.kickshub.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanpuh.kickshub.utils.validators.StatusConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @NotNull (message = "PRODUCT_FILES_NULL")
    @JsonIgnore
    List<MultipartFile> imgFiles;
}
