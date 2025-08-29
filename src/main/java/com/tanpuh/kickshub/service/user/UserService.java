package com.tanpuh.kickshub.service.user;

import com.tanpuh.kickshub.dto.request.UserCreationRequest;
import com.tanpuh.kickshub.dto.request.UserUpdateRequest;
import com.tanpuh.kickshub.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAll();
    UserResponse getById(Integer id);
    UserResponse create(UserCreationRequest dto);
    UserResponse update(UserUpdateRequest dto, Integer id);
    void delete(Integer id);
}
