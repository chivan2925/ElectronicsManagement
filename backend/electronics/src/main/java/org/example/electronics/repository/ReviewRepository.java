package org.example.electronics.repository;

import org.example.electronics.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

    @Query("SELECT r FROM ReviewEntity r WHERE r.product.id = :productId AND 1=1 " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(r.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(r.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " +

            "AND (:fromDate IS NULL OR r.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR r.createdAt <= :toDate)"
    )
    Page<ReviewEntity> findReviewsWithFilterByProductId(
            @Param("productId") Integer productId,
            @Param("keyword") String keyword,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
