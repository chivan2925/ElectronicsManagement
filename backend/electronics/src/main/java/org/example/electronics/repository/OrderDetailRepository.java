package org.example.electronics.repository;

import org.example.electronics.dto.response.TopProductDTO;
import org.example.electronics.entity.OrderDetailEntity;
import org.example.electronics.entity.OrderDetailId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, OrderDetailId> {
    List<OrderDetailEntity> findByOrderId(Integer orderId);

    @Query("SELECT new org.example.electronics.dto.response.TopProductDTO(" +
           "p.id, p.name, SUM(od.quantity)) " +
           "FROM OrderDetailEntity od " +
           "JOIN od.variant v " +
           "JOIN v.product p " +
           "JOIN od.order o " +
           "WHERE o.status = 'COMPLETED' " +
           "GROUP BY p.id, p.name " +
           "ORDER BY SUM(od.quantity) DESC")
    List<TopProductDTO> getTopSellingProducts(Pageable pageable);
}
