package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.PermissionRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.PermissionResponse;
import com.tanpuh.kickshub.service.user.PermissionService;
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
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission Controller")
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @GetMapping
    @Operation(summary = "get list of permissions")
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .data(permissionService.getAll())
                .message("Get list of permissions successfully")
                .build();
    }

    @PostMapping
    @Operation(summary = "create new permission")
    public ApiResponse<PermissionResponse> create(@RequestBody @Valid PermissionRequest dto) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.create(dto))
                .message("Create permission successfully")
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update permission by id")
    public ApiResponse<PermissionResponse> update(@RequestBody @Valid PermissionRequest dto, @PathVariable Integer id) {
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.update(dto, id))
                .message("Update permission successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete permission by id")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        permissionService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete permission successfully")
                .build();
    }
}
