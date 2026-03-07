package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ProductDetailResponseDTO(
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
        List<VariantResponseDTO> variants,
        List<MediaResponseDTO> media,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
