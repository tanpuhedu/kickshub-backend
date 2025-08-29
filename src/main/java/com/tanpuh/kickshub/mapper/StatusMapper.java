package com.tanpuh.kickshub.mapper;

import com.tanpuh.kickshub.utils.enums.EntityStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    default EntityStatus toEnum(Integer status) {
        return status == null ? null : EntityStatus.values()[status];
    }
}
