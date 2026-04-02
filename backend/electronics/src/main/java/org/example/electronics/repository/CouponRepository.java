package org.example.electronics.repository;

import org.example.electronics.entity.CouponEntity;
import org.example.electronics.entity.enums.CouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {

    boolean existsByCode(String code);

    boolean existsByCodeAndIdNot(String code, Integer id);

    @Query("SELECT c FROM CouponEntity c WHERE 1=1 " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(c.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(c.code) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " +

            "AND (:status IS NULL OR c.status = :status) " +

            "AND (:fromDate IS NULL OR c.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR c.createdAt <= :toDate)")
    Page<CouponEntity> findCouponsWithFilter(
            @Param("keyword") String keyword,
            @Param("status") CouponStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );

    @Modifying
    @Query("UPDATE CouponEntity c SET c.status = :expiredStatus " +
            "WHERE c.endsAt <= :now AND c.status = :validStatus")
    int updateExpiredCoupons(
            @Param("now") LocalDateTime now,
            @Param("expiredStatus") CouponStatus expiredStatus,
            @Param("validStatus") CouponStatus validStatus
    );
}
