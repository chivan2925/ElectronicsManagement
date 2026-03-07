package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VariantResponseDTO(
        Integer id,
        Integer productId,
        String name,
        String color,
        BigDecimal price,
        Integer stock,
        ProductStatus status,
        LocalDateTime createdAt
) {}
