package org.example.electronics.dto.response.admin;

import org.example.electronics.entity.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.Map;

public record AdminProductResponseDTO(
        Integer id,

        String name,

        String slug,

        Integer categoryId,

        String categoryName,

        Integer brandId,

        String brandName,

        String description,

        Map<String, Object> specsJson,

        Float ratingStar,

        Integer ratingCount,

        Integer warrantyMonths,

        ProductStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
