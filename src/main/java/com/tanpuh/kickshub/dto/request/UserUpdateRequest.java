package com.tanpuh.kickshub.dto.request;

import com.tanpuh.kickshub.utils.validators.PhoneConstraint;
import com.tanpuh.kickshub.utils.validators.StatusConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    Integer id;

    @Size(min = 8, message = "USER_PASSWORD_INVALID")
    String password;

    @NotBlank(message = "USER_FULLNAME_BLANK")
    String fullName;

    @NotNull(message = "USER_PHONE_NULL")
    @PhoneConstraint(message = "USER_PHONE_INVALID")
    String phone;

    @NotNull(message = "USER_MAIL_NULL")
    @Email(message = "USER_MAIL_INVALID")
    String email;

    @NotNull (message = "USER_STATUS_NULL")
    @StatusConstraint (min = 0, max = 1, message = "USER_STATUS_INVALID")
    Integer status;

    List<Integer> roleIds;
}
