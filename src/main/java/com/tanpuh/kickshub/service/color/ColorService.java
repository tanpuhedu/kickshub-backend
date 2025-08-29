package com.tanpuh.kickshub.service.color;

import com.tanpuh.kickshub.dto.request.ColorRequest;
import com.tanpuh.kickshub.dto.response.ColorResponse;

import java.util.List;

public interface ColorService {
    List<ColorResponse> getAll();

    ColorResponse getById(Integer id);

    ColorResponse create(ColorRequest request);

    ColorResponse update(ColorRequest request, Integer id);

    void delete(Integer id);
}
