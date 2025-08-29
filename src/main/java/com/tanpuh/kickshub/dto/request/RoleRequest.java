package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotBlank(message = "ROLE_NAME_BLANK")
    String name;

    @NotEmpty (message = "ROLE_PERMISSIONS_EMPTY")
    Set<@NotNull (message = "ROLE_PERMISSION_ID_NULL") Integer> permissionIds;
}
