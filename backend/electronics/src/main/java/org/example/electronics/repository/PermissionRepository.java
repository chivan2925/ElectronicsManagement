package org.example.electronics.repository;

import org.example.electronics.entity.PermissionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {

    boolean existsByCodeOrName(String code, String name);

    boolean existsByCodeAndIdNot(String code, Integer id);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Query("SELECT p FROM PermissionEntity p WHERE 1=1 " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(p.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(p.code) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " +

            "AND (:fromDate IS NULL OR p.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR p.createdAt <= :toDate)")
    Page<PermissionEntity> findPermissionsWithFilter(
            @Param("keyword") String keyword,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
