package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.ProductDetailCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductDetailUpdateRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.ProductDetailResponse;
import com.tanpuh.kickshub.service.product_detail.ProductDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product-details")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Product Detail Controller")
public class ProductDetailController {
    ProductDetailService productDetailService;

    @GetMapping
    @Operation(summary = "get list of product details")
    public ApiResponse<List<ProductDetailResponse>> getAll() {
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .data(productDetailService.getAll())
                .message("Get list of product details successfully")
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "get product detail by id")
    public ApiResponse<ProductDetailResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<ProductDetailResponse>builder()
                .data(productDetailService.getById(id))
                .message("Get product detail successfully")
                .build();
    }

    @PostMapping
    @Operation(summary = "create new product detail")
    public ApiResponse<ProductDetailResponse> create(@RequestBody @Valid ProductDetailCreationRequest request) {
        return ApiResponse.<ProductDetailResponse>builder()
                .message("Create product detail successfully")
                .data(productDetailService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "update product detail by id")
    public ApiResponse<ProductDetailResponse> update(
            @PathVariable Integer id,
            @RequestBody @Valid ProductDetailUpdateRequest request
    ) {
        return ApiResponse.<ProductDetailResponse>builder()
                .message("Update product detail successfully")
                .data(productDetailService.update(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete product detail by id")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        productDetailService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete product detail successfully")
                .build();
    }
}
