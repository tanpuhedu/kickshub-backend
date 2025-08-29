package com.tanpuh.kickshub.repository;

import com.tanpuh.kickshub.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer>{
}
