package org.example.electronics.dto.response.admin;

import org.example.electronics.entity.enums.CouponStatus;
import org.example.electronics.entity.enums.CouponType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AdminCouponResponseDTO(

        Integer id,
        Integer categoryId,
        Integer brandId,
        String code,
        CouponType type,
        BigDecimal value,
        BigDecimal minOrder,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        Integer usageLimit,
        BigDecimal maxDiscount,
        CouponStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
