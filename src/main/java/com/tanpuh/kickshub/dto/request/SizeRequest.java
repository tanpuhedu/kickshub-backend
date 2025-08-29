package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class SizeRequest {
    private Integer id; // not for creation, update

    @NotBlank (message = "SIZE_NAME_BLANK")
    private String name;
}
