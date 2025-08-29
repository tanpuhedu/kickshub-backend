package com.tanpuh.kickshub.service.user;

import com.tanpuh.kickshub.dto.request.RoleRequest;
import com.tanpuh.kickshub.dto.response.RoleResponse;
import com.tanpuh.kickshub.entity.Role;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.RoleMapper;
import com.tanpuh.kickshub.repository.PermissionRepository;
import com.tanpuh.kickshub.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper mapper;

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponse create(RoleRequest request) {
        Role role = mapper.toEntity(request);

        var permissions = permissionRepository.findAllById(request.getPermissionIds());
        role.setPermissions(new HashSet<>(permissions));

        return mapper.toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse update(RoleRequest request, Integer id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        mapper.update(role, request);
        var permissions = permissionRepository.findAllById(request.getPermissionIds());
        role.setPermissions(new HashSet<>(permissions));

        return mapper.toResponse(roleRepository.save(role));
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById((id));
    }
}
