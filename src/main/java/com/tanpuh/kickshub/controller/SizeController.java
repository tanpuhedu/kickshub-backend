package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.SizeRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.SizeResponse;
import com.tanpuh.kickshub.service.size.SizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sizes")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Size Controller")
public class SizeController {
    SizeService sizeService;

    @GetMapping
    @Operation(summary = "get list of sizes")
    public ApiResponse<List<SizeResponse>> getAll() {
        return ApiResponse.<List<SizeResponse>>builder()
                .message("Get list of sizes successfully")
                .data(sizeService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get size by id")
    public ApiResponse<SizeResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<SizeResponse>builder()
                .message("Get size successfully")
                .data(sizeService.getById(id))
                .build();
    }

    @PostMapping
    @Operation(summary = "create new size")
    public ApiResponse<SizeResponse> create(@RequestBody @Valid SizeRequest request) {
        return ApiResponse.<SizeResponse>builder()
                .message("Create size successfully")
                .data(sizeService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update size by id")
    public ApiResponse<SizeResponse> update(@PathVariable Integer id, @RequestBody @Valid SizeRequest request){
        return ApiResponse.<SizeResponse>builder()
                .message("Update size successfully")
                .data(sizeService.update(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete size by id")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        sizeService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete size successfully")
                .build();
    }
}
