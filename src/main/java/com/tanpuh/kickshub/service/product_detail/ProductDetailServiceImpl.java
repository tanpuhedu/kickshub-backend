package com.tanpuh.kickshub.service.product_detail;

import com.tanpuh.kickshub.dto.request.ProductDetailCreationRequest;
import com.tanpuh.kickshub.dto.request.ProductDetailUpdateRequest;
import com.tanpuh.kickshub.dto.response.ProductDetailResponse;
import com.tanpuh.kickshub.entity.Color;
import com.tanpuh.kickshub.entity.Product;
import com.tanpuh.kickshub.entity.ProductDetail;
import com.tanpuh.kickshub.entity.Size;
import com.tanpuh.kickshub.exception.AppException;
import com.tanpuh.kickshub.exception.ErrorCode;
import com.tanpuh.kickshub.mapper.ProductDetailMapper;
import com.tanpuh.kickshub.repository.ColorRepository;
import com.tanpuh.kickshub.repository.ProductDetailRepository;
import com.tanpuh.kickshub.repository.ProductRepository;
import com.tanpuh.kickshub.repository.SizeRepository;
import com.tanpuh.kickshub.utils.enums.EntityStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductDetailServiceImpl implements ProductDetailService {
    ProductDetailRepository productDetailRepository;
    ProductRepository productRepository;
    ColorRepository colorRepository;
    SizeRepository sizeRepository;
    ProductDetailMapper mapper;

    @Override
    public List<ProductDetailResponse> getAll() {
        return productDetailRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ProductDetailResponse getById(Integer id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));

        return mapper.toResponse(productDetail);
    }

    @Override
    @Transactional
    public ProductDetailResponse create(ProductDetailCreationRequest request) {
        if (productDetailRepository.existsByCode(request.getCode()))
            throw new AppException(ErrorCode.PRODUCT_DETAIL_EXISTED);

        Product product = productRepository.findById(request.getProduct().getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Size size = sizeRepository.findById(request.getSize().getId())
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

        Color color = colorRepository.findById(request.getColor().getId())
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

        ProductDetail productDetail = mapper.toEntity(request);
        productDetail.setStatus(EntityStatus.ACTIVE);
        productDetail.setSoldQty(0);
        productDetail.setProduct(product);
        productDetail.setSize(size);
        productDetail.setColor(color);
        productDetailRepository.save(productDetail);

        // xử lí stockQty của product
        product.setStockQty(product.getStockQty() + request.getStockQty());

        // xử lí status của product
        boolean hasActiveDetail = productDetailRepository
                .existsByProductIdAndStatus(product.getId(), EntityStatus.ACTIVE);
        product.setStatus(hasActiveDetail ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);

        // xử lí price của product
        Long minPrice = updateProductPrice(product);
        product.setPrice(minPrice);

        productRepository.save(product);

        return mapper.toResponse(productDetail);
    }

    @Override
    @Transactional
    public ProductDetailResponse update(ProductDetailUpdateRequest request, Integer id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));

        Product product = productRepository.findById(request.getProduct().getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Size size = sizeRepository.findById(request.getSize().getId())
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

        Color color = colorRepository.findById(request.getColor().getId())
                .orElseThrow(() -> new AppException(ErrorCode.COLOR_NOT_FOUND));

        mapper.update(productDetail, request);
        productDetail.setProduct(product);
        productDetail.setSize(size);
        productDetail.setColor(color);
        productDetail.setStockQty(productDetail.getStockQty() + request.getStockQty()); // xử lí stockQty của product detail
        productDetailRepository.save(productDetail);

        // xử lí stockQty của product
        product.setStockQty(product.getStockQty() + request.getStockQty());

        // xử lí status của product
        boolean hasActiveDetail = productDetailRepository
                .existsByProductIdAndStatus(product.getId(), EntityStatus.ACTIVE);
        product.setStatus(hasActiveDetail ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);

        // xử lí price của product
        Long minPrice = updateProductPrice(product);
        product.setPrice(minPrice);

        productRepository.save(product);

        return mapper.toResponse(productDetail);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_DETAIL_NOT_FOUND));

        Product product = productRepository.findById(productDetail.getProduct().getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // xử lí stockQty của product
        product.setStockQty(product.getStockQty() - productDetail.getStockQty());

        // xử lí status của product
        boolean hasActiveDetail = productDetailRepository
                .existsByProductIdAndStatus(product.getId(), EntityStatus.ACTIVE);
        product.setStatus(hasActiveDetail ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);

        // xử lí price của product
        Long minPrice = updateProductPrice(product);
        product.setPrice(minPrice);

        productRepository.save(product);

        productDetailRepository.deleteById(id);
    }

    private Long updateProductPrice(Product product) {
        return productDetailRepository.findByProductId(product.getId())
                .stream()
                .map(ProductDetail::getPrice)
                .min(Long::compareTo)
                .orElse(0L); // nếu xóa hết detail thì để 0
    }
}
