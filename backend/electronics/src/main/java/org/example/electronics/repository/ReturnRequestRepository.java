package org.example.electronics.repository;

import org.example.electronics.entity.ReturnRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequestEntity, Integer> {
    List<ReturnRequestEntity> findByUserIdOrderByCreatedAtDesc(Integer userId);
}
