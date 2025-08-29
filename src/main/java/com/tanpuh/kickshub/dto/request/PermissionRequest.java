package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor @AllArgsConstructor
public class PermissionRequest {
    @NotBlank (message = "PERMISSION_NAME_BLANK")
    private String name;
}
