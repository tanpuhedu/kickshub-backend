package com.tanpuh.kickshub.repository;

import com.tanpuh.kickshub.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    /*
    có 2 cách để generate câu SQL: native query, HQL/JPQL
    ví dụ: lấy danh sách sản phẩm dựa trên name, code, categoryId và có phân trang
    cách 1: - native query (viết syntax của db đang dùng => ko run đc khi dùng db khác)
            - trong vd này phải có attribute countQuery vì có phân trang
        @Query(
            value = """
                SELECT *
                FROM product p
                WHERE (:name IS NULL OR UPPER(p.name) LIKE CONCAT('%', UPPER(:name), '%'))
                  AND (:code IS NULL OR UPPER(p.code) LIKE CONCAT('%', UPPER(:code), '%'))
                  AND (:categoryId IS NULL OR p.category_id = :categoryId)
                """,
            countQuery = """
                SELECT COUNT(*)
                FROM product p
                WHERE (:name IS NULL OR UPPER(p.name) LIKE CONCAT('%', UPPER(:name), '%'))
                  AND (:code IS NULL OR UPPER(p.code) LIKE CONCAT('%', UPPER(:code), '%'))
                  AND (:categoryId IS NULL OR p.category_id = :categoryId)
                """,
            nativeQuery = true
        )
        Page<Product> search(
                @Param("name") String name,
                @Param("code") String code,
                @Param("categoryId") Integer categoryId,
                Pageable pageable
        );

    cách 2: - hibernate sẽ dịch câu HQL/JPQL này thành câu SQL tương ứng với db đang dùng
            - ko query phức tạp đc
        @Query("""
            select p from Product p
            where (:name is null or upper(p.name) like upper(concat('%',:name,'%')))
            and (:code is null or upper(p.code) like upper(concat('%',:code,'%')))
            and (:categoryId is null or p.category.id = :categoryId)
        """)
        Page<Product> search(
                @Param("name") String name,
                @Param("code") String code,
                @Param("categoryId") Integer categoryId,
                PageRequest pageable
        );
     */

//    dùng concat của HQL để nối 2 chuỗi bằng dấu phẩy, vì native query ko làm đc
    @Query("""
            select p from Product p
            where (:name is null or upper(p.name) like upper(concat('%',:name,'%')))
            and (:code is null or upper(p.code) like upper(concat('%',:code,'%')))
            and (:categoryId is null or p.category.id = :categoryId)
            """)
    Page<Product> search(
            @Param("name") String name,
            @Param("code") String code,
            @Param("categoryId") Integer categoryId,
            PageRequest pageable
    );

    boolean existsByCode(String code);
}
