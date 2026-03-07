package org.example.electronics.repository;

import org.example.electronics.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {
    boolean existsByName(String name);
}
