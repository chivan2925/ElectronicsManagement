package org.example.electronics.repository;

import org.example.electronics.entity.WarehouseTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseTransactionRepository extends JpaRepository<WarehouseTransactionEntity, Integer> {
    List<WarehouseTransactionEntity> findByWarehouseIdOrderByCreatedAtDesc(Integer warehouseId);
    List<WarehouseTransactionEntity> findByOrderId(Integer orderId);
}
