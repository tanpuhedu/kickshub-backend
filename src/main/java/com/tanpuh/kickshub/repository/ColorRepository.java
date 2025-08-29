package com.tanpuh.kickshub.repository;

import com.tanpuh.kickshub.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    boolean existsByName(String name);
}
