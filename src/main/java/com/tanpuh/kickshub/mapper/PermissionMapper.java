package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.request.PermissionRequest;
import com.tanpuh.kickshub.dto.response.PermissionResponse;
import com.tanpuh.kickshub.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toEntity(PermissionRequest dto);
    PermissionResponse toResponse(Permission entity);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Permission entity, PermissionRequest dto);
}
