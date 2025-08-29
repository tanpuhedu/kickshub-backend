package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.request.ColorRequest;
import com.tanpuh.kickshub.dto.response.ColorResponse;
import com.tanpuh.kickshub.entity.Color;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColorMapper {
    @Mapping(target = "id", ignore = true)
    Color toEntity(ColorRequest dto);

    ColorResponse toResponse(Color entity);

    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Color entity, ColorRequest dto);
}
