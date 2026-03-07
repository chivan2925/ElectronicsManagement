package org.example.electronics.repository;

import org.example.electronics.entity.MediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaRepository extends JpaRepository<MediaEntity, Integer> {
    List<MediaEntity> findByProductIdOrderBySortOrderAsc(Integer productId);

    @Modifying
    @Query("UPDATE MediaEntity m SET m.isPrimary = false WHERE m.product.id = :productId AND m.isPrimary = true")
    void clearPrimaryByProductId(Integer productId);
}
