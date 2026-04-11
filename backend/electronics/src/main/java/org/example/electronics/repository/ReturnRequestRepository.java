package org.example.electronics.repository;

import org.example.electronics.entity.ReturnRequestEntity;
import org.example.electronics.entity.enums.ReturnRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequestEntity, Integer> {

    @Query(value = "SELECT r FROM ReturnRequestEntity r " +
            "LEFT JOIN FETCH r.user " +
            "LEFT JOIN FETCH r.variant " +
            "WHERE 1=1 " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(r.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(r.reason) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " +

            "AND (:status IS NULL OR r.status = :status) " +

            "AND (CAST(:fromDate AS timestamp) IS NULL OR r.createdAt >= :fromDate) " +
            "AND (CAST(:toDate AS timestamp) IS NULL OR r.createdAt <= :toDate)",

            countQuery = "SELECT COUNT(r) FROM ReturnRequestEntity r " +
                    "WHERE 1=1 " +

                    "AND (:keyword IS NULL OR ( " +
                    "    CAST(r.id AS string) LIKE CONCAT('%', :keyword, '%') " +
                    "    OR LOWER(r.reason) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    ")) " +

                    "AND (:status IS NULL OR r.status = :status) " +

                    "AND (CAST(:fromDate AS timestamp) IS NULL OR r.createdAt >= :fromDate) " +
                    "AND (CAST(:toDate AS timestamp) IS NULL OR r.createdAt <= :toDate)"
    )
    Page<ReturnRequestEntity> findAllReturnRequestsWithFilter(
            @Param("keyword") String keyword,
            @Param("status") ReturnRequestStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
