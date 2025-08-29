package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CategoryRequest {
    private Integer id; // not for creation, update

    @NotBlank(message = "CATEGORY_NAME_BLANK")
    private String name;
}
