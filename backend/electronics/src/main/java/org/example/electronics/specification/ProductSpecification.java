package org.example.electronics.specification;

import jakarta.persistence.criteria.JoinType;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.entity.enums.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<ProductEntity> hasCategory(Integer categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return null;
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }

    public static Specification<ProductEntity> hasBrand(Integer brandId) {
        return (root, query, cb) -> {
            if (brandId == null) return null;
            return cb.equal(root.get("brand").get("id"), brandId);
        };
    }

    public static Specification<ProductEntity> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    public static Specification<ProductEntity> hasPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) return null;
            var variants = root.join("variants", JoinType.INNER);
            if (minPrice != null && maxPrice != null) {
                return cb.between(variants.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return cb.greaterThanOrEqualTo(variants.get("price"), minPrice);
            } else {
                return cb.lessThanOrEqualTo(variants.get("price"), maxPrice);
            }
        };
    }

    public static Specification<ProductEntity> hasStatus(ProductStatus status) {
        return (root, query, cb) -> {
            if (status == null) return cb.equal(root.get("status"), ProductStatus.ACTIVE);
            return cb.equal(root.get("status"), status);
        };
    }
}
