package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class ColorRequest {
    private Integer id; // not for creation, update

    @NotBlank (message = "COLOR_NAME_BLANK")
    private String name;
}
