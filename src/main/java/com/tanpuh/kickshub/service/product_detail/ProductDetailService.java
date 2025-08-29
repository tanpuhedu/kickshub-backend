package com.tanpuh.kickshub.service.product_detail;

import com.tanpuh.kickshub.dto.request.ProductDetailCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductDetailUpdateRequest;
import com.tanpuh.kickshub.dto.response.ProductDetailResponse;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetailResponse> getAll();
    ProductDetailResponse getById(Integer id);
    ProductDetailResponse create(ProductDetailCreationRequest request);
    ProductDetailResponse update(ProductDetailUpdateRequest request, Integer id);
    void delete(Integer id);

}
