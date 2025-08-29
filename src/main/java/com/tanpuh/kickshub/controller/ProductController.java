package com.tanpuh.kickshub.controller;

import com.tanpuh.kickshub.dto.request.ProductCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductUpdateRequest;
import com.tanpuh.kickshub.dto.response.ApiResponse;
import com.tanpuh.kickshub.dto.response.ProductResponse;
import com.tanpuh.kickshub.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

//    @GetMapping
//    public ApiResponse<List<ProductResponse>> getAll(){
//        return ApiResponse.<List<ProductResponse>>builder()
//                .data(productService.getAll())
//                .message("Get list of products successfully")
//                .build();
//    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllByCriteria(
            @RequestParam(value = "sortBy", required = false) List<String> sortBy,
            @RequestParam(value = "sortDir", required = false) String sortDir,
            @RequestParam(value = "pageIdx", required = false) Integer pageIdx,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
            ) {
        return ApiResponse.<List<ProductResponse>>builder()
                .data(productService.getAllByCriteria(sortBy, sortDir, pageIdx, pageSize))
                .message("Get list of products by criteria successfully")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable Integer id) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.getById(id))
                .message("Get product successfully")
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> search(
//            required = false là từ khoá có thể truyền vào hoặc không truyền vào, cho phép null
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "pageIdx", required = false) Integer pageIdx,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        return ApiResponse.<List<ProductResponse>>builder()
                .data(productService.search(name, code, categoryId, pageIdx, pageSize))
                .message("Get list of products successfully")
                .build();
    }

    @PostMapping
    public ApiResponse<ProductResponse> create(@ModelAttribute @Valid ProductCreationRequest request) {
        // @ModelAttribute có thể hứng được file còn @RequestBody thì không
        // Chọn form data để nhập thay vì JSON
        return ApiResponse.<ProductResponse>builder()
                .message("Create product successfully")
                .data(productService.create(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(
            @PathVariable Integer id,
            @ModelAttribute @Valid ProductUpdateRequest request
    ) {
        return ApiResponse.<ProductResponse>builder()
                .message("Update product successfully")
                .data(productService.update(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ApiResponse.<Void>builder()
                .message("Delete product successfully")
                .build();
    }
}
