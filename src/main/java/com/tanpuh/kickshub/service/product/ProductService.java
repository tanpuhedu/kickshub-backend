package com.tanpuh.kickshub.service.product;

import com.tanpuh.kickshub.dto.request.ProductCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductUpdateRequest;
import com.tanpuh.kickshub.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();
    List<ProductResponse> getAllByCriteria(List<String> sortBy, String sortDir, Integer pageIdx, Integer pageSize);
    List<ProductResponse> search(String name, String code, Integer categoryId, Integer pageIdx, Integer pageSize);
    ProductResponse getById(Integer id);
    ProductResponse create(ProductCreationRequest request);
    ProductResponse update(ProductUpdateRequest request, Integer id);
    void delete(Integer id);
}
