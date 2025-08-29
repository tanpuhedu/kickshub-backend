package com.tanpuh.kickshub.service.user;

import com.tanpuh.kickshub.dto.request.RoleRequest;
import com.tanpuh.kickshub.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getAll();
    RoleResponse create(RoleRequest dto);
    RoleResponse update(RoleRequest dto, Integer id);
    void delete(Integer id);
}
