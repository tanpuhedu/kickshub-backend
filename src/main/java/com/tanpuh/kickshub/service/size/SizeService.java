package com.tanpuh.kickshub.service.size;

import com.tanpuh.kickshub.dto.request.SizeRequest;
import com.tanpuh.kickshub.dto.response.SizeResponse;

import java.util.List;

public interface SizeService {

    List<SizeResponse> getAll();

    SizeResponse getById(Integer id);

    SizeResponse create(SizeRequest request);

    SizeResponse update(SizeRequest request, Integer id);

    void delete(Integer id);
}
