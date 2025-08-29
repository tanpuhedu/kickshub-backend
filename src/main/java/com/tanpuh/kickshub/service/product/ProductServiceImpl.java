package com.tanpuh.kickshub.service.product;

import com.tanpuh.kickshub.dto.request.ProductCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductUpdateRequest;
import com.tanpuh.kickshub.dto.response.ProductResponse;
import com.tanpuh.kickshub.entity.Category;
import com.tanpuh.kickshub.entity.Product;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.ProductMapper;
import com.tanpuh.kickshub.repository.CategoryRepository;
import com.tanpuh.kickshub.repository.ProductDetailRepository;
import com.tanpuh.kickshub.repository.ProductRepository;
import com.tanpuh.kickshub.service.cloudinary.CloudinaryService;
import com.tanpuh.kickshub.utils.enums.EntityStatus;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    ProductDetailRepository productDetailRepository;
    ProductMapper mapper;
    CloudinaryService cloudinaryService;

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> getAllByCriteria(List<String> sortBy, String sortDir,
                                                  Integer pageIdx, Integer pageSize) {
        pageIdx = (pageIdx == null) ? 0 : pageIdx;
        pageSize = (pageSize == null) ? 5 : pageSize;

        Pageable pageable;

        if (sortBy == null || sortBy.isEmpty()) {
            // Nếu không có tham số sortBy -> mặc định sort theo id
            pageable = PageRequest.of(pageIdx, pageSize, Sort.Direction.valueOf(sortDir.toUpperCase()), "id");
        } else {
            // Ghép nhiều tiêu chí
            // Nếu không truyền sortDir thì mặc định ASC
            Sort.Direction direction = (sortDir != null && sortDir.equalsIgnoreCase("desc"))
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            // Áp dụng cùng 1 sortDir cho tất cả field trong sortBy
            List<Sort.Order> orders = sortBy
                    .stream()
                    .map(field -> new Sort.Order(direction, field))
                    .toList();
            pageable = PageRequest.of(pageIdx, pageSize, Sort.by(orders));
        }

        Page<Product> pageEntity = productRepository.findAll(pageable);

        return pageEntity.getContent()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ProductResponse> search(String name, String code, Integer categoryId,
                                        Integer pageIdx, Integer pageSize) {
        pageIdx = (pageIdx == null) ? 0 : pageIdx;
        pageSize = (pageSize == null) ? 5 : pageSize;

        Page<Product> pageEntity = productRepository
                .search(name, code, categoryId, PageRequest.of(pageIdx, pageSize));

        return pageEntity.getContent() // pageEntity.getContent() return List<T> T là sản phẩm được lấy ra
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        return mapper.toResponse(product);
    }

    @Override
    public ProductResponse create(ProductCreationRequest request) {
        if (productRepository.existsByCode(request.getCode()))
            throw new AppException(ErrorCode.PRODUCT_EXISTED);

        Category category = categoryRepository.findById(request.getCategory().getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Product product = mapper.toEntity(request);
        product.setPrice(0L);
        product.setStockQty(0);
        product.setSoldQty(0);
        product.setStatus(EntityStatus.INACTIVE);
        product.setCategory(category);
        product.setImgURLs(uploadImagesToCloudinary(request.getImgFiles()));

        return mapper.toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse update(ProductUpdateRequest request, Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Category category = categoryRepository.findById(request.getCategory().getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        mapper.update(product, request);
        product.setCategory(category);
        product.setImgURLs(uploadImagesToCloudinary(request.getImgFiles()));

        return mapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productDetailRepository.deleteAllByProductId(product.getId());

        productRepository.deleteById(id);
    }

    private List<String> uploadImagesToCloudinary(List<MultipartFile> files) {
        List<String> imgURLs = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = cloudinaryService.upload(file, "images");

            if (url == null)
                throw new RuntimeException("Uploading fail");

            imgURLs.add(url);
        }
        return imgURLs;
    }
}
