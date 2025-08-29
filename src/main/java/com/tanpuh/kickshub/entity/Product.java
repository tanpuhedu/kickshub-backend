package com.tanpuh.kickshub.entity;

import com.tanpuh.kickshub.utils.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
}
