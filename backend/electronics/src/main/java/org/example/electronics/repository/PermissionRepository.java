package org.example.electronics.repository;

import org.example.electronics.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {
    boolean existsByName(String name);
}
