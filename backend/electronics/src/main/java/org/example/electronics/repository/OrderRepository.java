package org.example.electronics.repository;

import org.example.electronics.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.example.electronics.dto.response.RevenueReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer>, JpaSpecificationExecutor<OrderEntity> {
    List<OrderEntity> findByUserIdOrderByCreatedAtDesc(Integer userId);

    @Query("SELECT new org.example.electronics.dto.response.RevenueReportDTO(" +
           "CAST(o.createdAt AS localdate), SUM(o.total), COUNT(o.id)) " +
           "FROM OrderEntity o " +
           "WHERE o.status = 'COMPLETED' " +
           "AND o.createdAt >= :startDate AND o.createdAt <= :endDate " +
           "GROUP BY CAST(o.createdAt AS localdate) " +
           "ORDER BY CAST(o.createdAt AS localdate) ASC")
    List<RevenueReportDTO> getRevenueByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
