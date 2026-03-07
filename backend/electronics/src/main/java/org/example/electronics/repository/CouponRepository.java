package org.example.electronics.repository;

import org.example.electronics.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<CouponEntity, Integer> {
    Optional<CouponEntity> findByCode(String code);
    boolean existsByCode(String code);
}
