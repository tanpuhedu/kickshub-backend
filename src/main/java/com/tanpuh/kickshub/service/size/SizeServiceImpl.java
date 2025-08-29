package com.tanpuh.kickshub.service.size;

import com.tanpuh.kickshub.dto.request.SizeRequest;
import com.tanpuh.kickshub.dto.response.SizeResponse;
import com.tanpuh.kickshub.entity.Size;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.SizeMapper;
import com.tanpuh.kickshub.repository.SizeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SizeServiceImpl implements SizeService {
    SizeRepository sizeRepository;
    SizeMapper mapper;

    @Override
    public List<SizeResponse> getAll() {
        return sizeRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public SizeResponse getById(Integer id){
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

        return mapper.toResponse(size);
    }

    @Override
    public SizeResponse create(SizeRequest request) {
        if (sizeRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.SIZE_EXISTED);

        Size size = mapper.toEntity(request);

        return mapper.toResponse(sizeRepository.save(size));
    }

    @Override
    public SizeResponse update(SizeRequest request, Integer id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

        mapper.update(size, request);

        return mapper.toResponse(sizeRepository.save(size));
    }

    @Override
    public void delete(Integer id) {
        sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

        sizeRepository.deleteById(id);
    }

}
