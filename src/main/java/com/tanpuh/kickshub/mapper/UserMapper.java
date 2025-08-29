package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.request.UserCreationRequest;
import com.tanpuh.kickshub.dto.request.UserUpdateRequest;
import com.tanpuh.kickshub.dto.response.UserResponse;
import com.tanpuh.kickshub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, StatusMapper.class})
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phone", ignore = true)
    User toEntity(UserCreationRequest dto);

    UserResponse toResponse(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void update(@MappingTarget User entity, UserUpdateRequest dto);
}
