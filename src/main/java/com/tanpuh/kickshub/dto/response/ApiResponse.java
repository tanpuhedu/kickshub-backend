package com.tanpuh.kickshub.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL )
public class ApiResponse<T>{
    @Builder.Default
    private Integer status = 1000; // trạng thái mặc định khi CRUD thành công

    private String message;
    private T data;
}
