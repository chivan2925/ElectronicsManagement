package org.example.electronics.repository;

import org.example.electronics.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<WarehouseEntity, Integer> {
    boolean existsByName(String name);
}
