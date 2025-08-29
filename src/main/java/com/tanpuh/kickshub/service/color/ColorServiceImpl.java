package com.tanpuh.kickshub.service.color;

import com.tanpuh.kickshub.dto.request.ColorRequest;
import com.tanpuh.kickshub.dto.response.ColorResponse;
import com.tanpuh.kickshub.entity.Color;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.ColorMapper;
import com.tanpuh.kickshub.repository.ColorRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ColorServiceImpl implements ColorService {
    ColorRepository colorRepository;
    ColorMapper mapper;

    @Override
    public List<ColorResponse> getAll() {
        return colorRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ColorResponse getById(Integer id){
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

        return mapper.toResponse(color);
    }

    @Override
    public ColorResponse create(ColorRequest request) {
        if (colorRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.COLOR_EXISTED);

        Color color = mapper.toEntity(request);

        return mapper.toResponse(colorRepository.save(color));
    }

    @Override
    public ColorResponse update(ColorRequest request, Integer id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

        mapper.update(color, request);

        return mapper.toResponse(colorRepository.save(color));
    }

    @Override
    public void delete(Integer id) {
        colorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

        colorRepository.deleteById(id);
    }
}
