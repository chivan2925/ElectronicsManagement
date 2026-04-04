package org.example.electronics.repository;

import org.example.electronics.entity.enums.*;
import org.example.electronics.entity.order.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT o FROM OrderEntity o WHERE 1=1 " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(o.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(o.code) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(o.shippingName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR o.shippingPhone LIKE CONCAT('%', :keyword, '%') " +
            ")) " +

            "AND (:status IS NULL OR o.status = :status) " +
            "AND (:type IS NULL OR o.paymentMethod = :method) " +
            "AND (:paymentStatus IS NULL OR o.paymentStatus = :paymentStatus) " +
            "AND (:provider IS NULL OR o.shippingProvider = :provider) " +
            "AND (:shippingStatus IS NULL OR o.shippingStatus = :shippingStatus) " +

            "AND (:fromDate IS NULL OR o.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR o.createdAt <= :toDate)")
    Page<OrderEntity> findOrdersWithFilter(
            @Param("keyword") String keyword,
            @Param("status") OrderStatus status,
            @Param("method") PaymentMethodType type,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("provider") ShippingProvider provider,
            @Param("shippingStatus") ShippingStatus shippingStatus,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"user", "coupon", "orderDetails", "orderDetails.variant", "orderDetails.variant.product"})
    @Query("SELECT o FROM OrderEntity o WHERE o.id = :id")
    Optional<OrderEntity> findOrderByIdWithDetails(@Param("id") Integer orderId);
}
