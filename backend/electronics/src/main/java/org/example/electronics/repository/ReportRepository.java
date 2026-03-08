package org.example.electronics.repository;

import org.example.electronics.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {
    List<ReportEntity> findByUserIdOrderByCreatedAtDesc(Integer userId);
}
