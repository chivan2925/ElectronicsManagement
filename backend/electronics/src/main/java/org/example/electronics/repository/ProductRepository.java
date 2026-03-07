package org.example.electronics.repository;

import org.example.electronics.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {
    Optional<ProductEntity> findBySlug(String slug);
    boolean existsBySlug(String slug);

    @Query("SELECT p FROM ProductEntity p LEFT JOIN FETCH p.variants LEFT JOIN FETCH p.media WHERE p.slug = :slug")
    Optional<ProductEntity> findBySlugWithDetails(String slug);
}
