package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.SizeRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.SizeResponse;
import com.tanpuh.kickshub.service.size.SizeService;
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
public class SizeController {
    SizeService sizeService;

    @GetMapping
    public ApiResponse<List<SizeResponse>> getAll() {
        return ApiResponse.<List<SizeResponse>>builder()
                .message("Get list of sizes successfully")
                .data(sizeService.getAll())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SizeResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<SizeResponse>builder()
                .message("Get size successfully")
                .data(sizeService.getById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<SizeResponse> create(@RequestBody @Valid SizeRequest request) {
        return ApiResponse.<SizeResponse>builder()
                .message("Create size successfully")
                .data(sizeService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SizeResponse> update(@PathVariable Integer id, @RequestBody @Valid SizeRequest request){
        return ApiResponse.<SizeResponse>builder()
                .message("Update size successfully")
                .data(sizeService.update(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        sizeService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete size successfully")
                .build();
    }
}
