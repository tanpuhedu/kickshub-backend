package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.request.SizeRequest;
import com.tanpuh.kickshub.dto.response.SizeResponse;
import com.tanpuh.kickshub.entity.Size;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SizeMapper {
    @Mapping(target = "id", ignore = true)
    Size toEntity(SizeRequest dto);

    SizeResponse toResponse(Size entity);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Size entity, SizeRequest dto);
}
