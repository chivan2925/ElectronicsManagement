package org.example.electronics.repository;

import org.example.electronics.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    //Admin
    boolean existsBySlug(String slug);

    boolean existsById(Integer id);

    boolean existsBySlugAndIdNot(String slug, Integer id);

    boolean existsByParentId(Integer parentId);

    Page<CategoryEntity> findByParentIdIsNull(Pageable pageable);

    Page<CategoryEntity> findByParentId(Integer parentId, Pageable pageable);
}
