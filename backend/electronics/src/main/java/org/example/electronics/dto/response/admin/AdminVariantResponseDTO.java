package org.example.electronics.dto.response.admin;

import org.example.electronics.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record AdminVariantResponseDTO(
        Integer id,

        Integer productId,

        String productName,

        List<AdminMediaResponseDTO> media,

        String name,

        String slug,

        String color,

        Map<String, Object> specsJson,

        BigDecimal price,

        Integer stock,

        ProductStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
