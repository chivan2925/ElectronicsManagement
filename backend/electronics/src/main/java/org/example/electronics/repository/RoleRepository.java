package org.example.electronics.repository;

import org.example.electronics.entity.RoleEntity;
import org.example.electronics.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);

    @Query("SELECT r FROM RoleEntity r WHERE 1=1 " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(r.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " +

            "AND (:status IS NULL OR r.status = :status) " +

            "AND (:fromDate IS NULL OR r.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR r.createdAt <= :toDate)")
    Page<RoleEntity> findRolesWithFilter(
            @Param("keyword") String keyword,
            @Param("status") UserStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
