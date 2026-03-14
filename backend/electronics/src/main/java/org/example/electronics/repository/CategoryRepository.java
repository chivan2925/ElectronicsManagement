package org.example.electronics.repository;

import org.example.electronics.entity.CategoryEntity;
import org.example.electronics.entity.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    //Admin
    boolean existsBySlug(String slug);

    boolean existsById(Integer id);

    boolean existsBySlugAndIdNot(String slug, Integer id);

    boolean existsByParent_Id(Integer parentId);

    @Query("SELECT c FROM CategoryEntity c WHERE c.parent IS NULL " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(c.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(c.slug) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " +

            "AND (:status IS NULL OR c.status = :status) " +

            "AND (:fromDate IS NULL OR c.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR c.createdAt <= :toDate)")
    Page<CategoryEntity> findParentCategoriesWithFilter(
            @Param("keyword") String keyword,
            @Param("status") ProductStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );

    @Query("SELECT c FROM CategoryEntity c WHERE c.parent.id = :parentId " +

            "AND (:keyword IS NULL OR ( " +
            "    CAST(c.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "    OR LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "    OR LOWER(c.slug) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")) " +

            "AND (:status IS NULL OR c.status = :status) " +

            "AND (:fromDate IS NULL OR c.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR c.createdAt <= :toDate)")
    Page<CategoryEntity> findSubCategoriesWithFilter(
            @Param("parentId") Integer parentId,
            @Param("keyword") String keyword,
            @Param("status") ProductStatus status,
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );
}
