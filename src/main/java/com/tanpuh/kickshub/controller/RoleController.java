package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.RoleRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.RoleResponse;
import com.tanpuh.kickshub.service.user.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Role Controller")
@Slf4j
public class RoleController {
    RoleService roleService;

    @GetMapping
    @Operation(summary = "get list of roles")
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.getAll())
                .message("Get list of roles successfully")
                .build();
    }

    @PostMapping
    @Operation(summary = "create new role")
    ApiResponse<RoleResponse> create(@RequestBody @Valid RoleRequest dto) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.create(dto))
                .message("Create role successfully")
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update role by id")
    ApiResponse<RoleResponse> update(@RequestBody @Valid RoleRequest dto, @PathVariable Integer id) {
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.update(dto, id))
                .message("Update role successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete role by id")
    ApiResponse<Void> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete role successfully")
                .build();
    }
}