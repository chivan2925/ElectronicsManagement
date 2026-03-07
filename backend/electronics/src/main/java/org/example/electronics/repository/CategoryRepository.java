package org.example.electronics.repository;

import org.example.electronics.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findByParentIsNull();
    boolean existsBySlug(String slug);
}
