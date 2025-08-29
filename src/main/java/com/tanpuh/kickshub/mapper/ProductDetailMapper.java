package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.request.ProductDetailCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductDetailUpdateRequest;
import com.tanpuh.kickshub.dto.response.ProductDetailResponse;
import com.tanpuh.kickshub.entity.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",
        uses = {SizeMapper.class, ColorMapper.class, ProductMapper.class, StatusMapper.class})
public interface ProductDetailMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "product", ignore = true)
    ProductDetail toEntity(ProductDetailCreationRequest dto);

    ProductDetailResponse toResponse(ProductDetail entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "stockQty", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "product", ignore = true)
    void update(@MappingTarget ProductDetail entity, ProductDetailUpdateRequest dto);
}
