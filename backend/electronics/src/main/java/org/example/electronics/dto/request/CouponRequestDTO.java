package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.example.electronics.entity.enums.CouponType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CouponRequestDTO(
        @NotBlank(message = "Mã coupon không được để trống")
        String code,

        @NotNull(message = "Loại coupon không được để trống")
        CouponType type,

        @NotNull(message = "Giá trị không được để trống")
        @Positive(message = "Giá trị phải lớn hơn 0")
        BigDecimal value,

        BigDecimal minOrder,

        BigDecimal maxDiscount,

        LocalDateTime startsAt,

        LocalDateTime endsAt,

        Integer usageLimit,

        Integer categoryId,

        Integer brandId
) {}
