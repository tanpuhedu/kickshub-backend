package com.tanpuh.kickshub.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class RefreshRequest {
    private String token;
}
