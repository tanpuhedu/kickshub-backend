package com.tanpuh.kickshub.service.user;

import com.tanpuh.kickshub.dto.request.PermissionRequest;
import com.tanpuh.kickshub.dto.response.PermissionResponse;
import com.tanpuh.kickshub.entity.Permission;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.PermissionMapper;
import com.tanpuh.kickshub.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper mapper;

    @Override
    public List<PermissionResponse> getAll() {
        return permissionRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = mapper.toEntity(request);
        return mapper.toResponse(permissionRepository.save(permission));
    }

    @Override
    public PermissionResponse update(PermissionRequest request, Integer id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        mapper.update(permission, request);

        return mapper.toResponse(permissionRepository.save(permission));
    }

    @Override
    public void delete(Integer id) {
        permissionRepository.deleteById(id);
    }
}
