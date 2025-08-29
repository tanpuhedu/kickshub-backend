package com.tanpuh.kickshub.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @NotNull (message = "PRODUCT_FILES_NULL")
    @JsonIgnore
    List<MultipartFile> imgFiles;
}
