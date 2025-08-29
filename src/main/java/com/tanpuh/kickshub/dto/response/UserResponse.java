package com.tanpuh.kickshub.dto.response;

import com.tanpuh.kickshub.utils.enums.EntityStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Integer id;
    String username;
    String fullName;
    String phone;
    String email;
    EntityStatus status;
    Set<RoleResponse> roles;
}