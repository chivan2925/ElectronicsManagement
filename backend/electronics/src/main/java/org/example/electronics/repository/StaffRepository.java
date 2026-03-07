package org.example.electronics.repository;

import org.example.electronics.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffEntity, Integer> {
    Optional<StaffEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
