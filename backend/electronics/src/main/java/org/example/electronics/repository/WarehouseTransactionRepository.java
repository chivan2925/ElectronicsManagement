package org.example.electronics.repository;

import org.example.electronics.entity.enums.WarehouseTransactionType;
import org.example.electronics.entity.order.OrderEntity;
import org.example.electronics.entity.warehouse.transaction.WarehouseTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseTransactionRepository extends JpaRepository<WarehouseTransactionEntity, Integer> {

    Optional<WarehouseTransactionEntity> findByOrderAndType(OrderEntity orderEntity, WarehouseTransactionType type);
}
