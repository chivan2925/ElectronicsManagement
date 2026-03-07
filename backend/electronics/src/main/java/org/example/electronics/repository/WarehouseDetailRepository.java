package org.example.electronics.repository;

import org.example.electronics.entity.WarehouseDetailEntity;
import org.example.electronics.entity.WarehouseDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WarehouseDetailRepository extends JpaRepository<WarehouseDetailEntity, WarehouseDetailId> {
    List<WarehouseDetailEntity> findByWarehouseId(Integer warehouseId);
    Optional<WarehouseDetailEntity> findByWarehouseIdAndVariantId(Integer warehouseId, Integer variantId);
}
