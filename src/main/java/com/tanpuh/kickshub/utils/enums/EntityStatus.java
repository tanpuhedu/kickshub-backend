package com.tanpuh.kickshub.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EntityStatus {
    INACTIVE(0),
    ACTIVE(1);

    private final int code;
}
