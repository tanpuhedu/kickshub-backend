package com.tanpuh.kickshub.service.category;

import com.tanpuh.kickshub.dto.request.CategoryRequest;
import com.tanpuh.kickshub.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();

    CategoryResponse getById(Integer id);

    CategoryResponse create(CategoryRequest request);

    CategoryResponse update(CategoryRequest request, Integer id);

    void delete(Integer id);
}
