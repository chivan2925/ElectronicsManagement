package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.CouponType;
import org.example.electronics.entity.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponResponseDTO(
        Integer id,
        String code,
        CouponType type,
        BigDecimal value,
        BigDecimal minOrder,
        BigDecimal maxDiscount,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        Integer usageLimit,
        Integer usedCount,
        ProductStatus status,
        Integer categoryId,
        Integer brandId,
        LocalDateTime createdAt
) {}
