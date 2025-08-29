package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.request.RoleRequest;
import com.tanpuh.kickshub.dto.response.RoleResponse;
import com.tanpuh.kickshub.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(RoleRequest dto);

    RoleResponse toResponse(Role entity);

    @Mapping(target = "permissions", ignore = true)
    void update(@MappingTarget Role entity, RoleRequest dto);
}
