package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.ColorRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.ColorResponse;
import com.tanpuh.kickshub.service.color.ColorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorController {
    ColorService colorService;

    @GetMapping
    public ApiResponse<List<ColorResponse>> getAll() {
        return ApiResponse.<List<ColorResponse>>builder()
                .data(colorService.getAll())
                .message("Get list of colors successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ColorResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<ColorResponse>builder()
                .data(colorService.getById(id))
                .message("Get color successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<ColorResponse> create(@RequestBody @Valid ColorRequest colorRequest) {
        return ApiResponse.<ColorResponse>builder()
                .message("Create color successfully")
                .data(colorService.create(colorRequest))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ColorResponse> update(@PathVariable Integer id, @RequestBody @Valid ColorRequest colorRequest) {
        return ApiResponse.<ColorResponse>builder()
                .message("Update color successfully")
                .data(colorService.update(colorRequest, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id){
        colorService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete color successfully")
                .build();
    }
}
