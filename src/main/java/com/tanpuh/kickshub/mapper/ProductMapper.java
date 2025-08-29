package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.request.ProductCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductUpdateRequest;
import com.tanpuh.kickshub.dto.response.ProductResponse;
import com.tanpuh.kickshub.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, StatusMapper.class})
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductCreationRequest dto);

    ProductResponse toResponse(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockQty", ignore = true)
    @Mapping(target = "category", ignore = true)
    void update(@MappingTarget Product entity, ProductUpdateRequest dto);
}

