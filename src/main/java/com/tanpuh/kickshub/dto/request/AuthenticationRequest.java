package com.tanpuh.kickshub.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class AuthenticationRequest {
    @NotNull (message = "USERNAME_NULL")
    private String username;

    @NotNull (message = "PASSWORD_NULL")
    private String password;
}
