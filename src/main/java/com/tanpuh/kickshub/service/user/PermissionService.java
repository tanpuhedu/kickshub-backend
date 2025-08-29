package com.tanpuh.kickshub.service.user;

import com.tanpuh.kickshub.dto.request.PermissionRequest;
import com.tanpuh.kickshub.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    List<PermissionResponse> getAll();
    PermissionResponse create(PermissionRequest dto);
    PermissionResponse update(PermissionRequest dto, Integer id);
    void delete(Integer id);
}
