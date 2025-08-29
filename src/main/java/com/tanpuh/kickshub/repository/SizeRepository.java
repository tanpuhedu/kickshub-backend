package com.tanpuh.kickshub.repository;

import com.tanpuh.kickshub.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Integer> {
    boolean existsByName(String name);
}
