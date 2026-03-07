package org.example.electronics.repository;

import org.example.electronics.entity.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariantRepository extends JpaRepository<VariantEntity, Integer> {
    List<VariantEntity> findByProductId(Integer productId);

    List<VariantEntity> findByStockLessThan(Integer threshold);
}
