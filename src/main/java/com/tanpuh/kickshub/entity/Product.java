package com.tanpuh.kickshub.entity;

import com.tanpuh.kickshub.utils.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String code;
    String name;
    Long price;
    Integer stockQty;
    Integer soldQty;
    String description;

    @Enumerated
    EntityStatus status;

    @ManyToOne
    Category category;

    @ElementCollection // tạo bảng phụ join với bảng chính, để map các giá trị ko phải entity (ko có @Entity)
            (fetch = FetchType.EAGER) // load bảng phụ cùng lúc load bảng chính
//            (fetch = FetchType.LAZY) // chỉ load bảng phụ khi được gọi
    @CollectionTable(name = "img_url")
    List<String> imgURLs;
}
