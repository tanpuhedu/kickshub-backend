package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.CategoryRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.CategoryResponse;
import com.tanpuh.kickshub.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Category Controller")
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    @Operation(summary = "get list of categories")
    public ApiResponse<List<CategoryResponse>> getAll() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .data(categoryService.getAll())
                .message("Get list of categories successfully")
                .build();
    }


    @GetMapping("/{id}")
    @Operation(summary = "get category by id")
    public ApiResponse<CategoryResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Get category successfully")
                .data(categoryService.getById(id))
                .build();
    }

    @PostMapping
    @Operation(summary = "create new category")
    public ApiResponse<CategoryResponse> create(@RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Create category successfully")
                .data(categoryService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update category by id")
    public ApiResponse<CategoryResponse> update(@PathVariable Integer id, @RequestBody @Valid CategoryRequest request) {
        return ApiResponse.<CategoryResponse>builder()
                .message("Update category successfully")
                .data(categoryService.update(request, id))
                .build();

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete category by id")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete category successfully")
                .build();
    }
}
