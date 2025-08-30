package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.dto.response.CartItemResponse;
import com.tanpuh.kickshub.entity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemResponse toResponse(CartItem entity);
}
