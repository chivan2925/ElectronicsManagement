package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDTO(
        Integer id,
        String name,
        String slug,
        Integer categoryId,
        String categoryName,
        Integer brandId,
        String brandName,
        String description,
        Float ratingStar,
        Integer ratingCount,
        ProductStatus status,
        String primaryImage,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
