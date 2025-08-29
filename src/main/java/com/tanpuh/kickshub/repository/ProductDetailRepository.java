package com.tanpuh.kickshub.repository;


import com.tanpuh.kickshub.entity.ProductDetail;
import com.tanpuh.kickshub.utils.enums.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    boolean existsByCode(String code);

    /*
    Trong bảng ProductDetail, có tồn tại record nào của productId này mà có status = 1 hay không?
    Nếu có ít nhất 1, return true
    Nếu không có, return false
    SELECT
       CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
    FROM product_detail
    WHERE product_id = :productId AND status = :status;
    */
    boolean existsByProductIdAndStatus(Integer productId, EntityStatus status);

    List<ProductDetail> findByProductId(Integer productId);

    @Modifying // để Spring Data biết đây ko p query SELECT mà là query thay đổi dữ liệu (UPDATE/DELETE)
    @Query(value = "DELETE FROM product_detail WHERE product_id = :productId", nativeQuery = true)
    void deleteAllByProductId(@Param("productId") Integer productId);
}
