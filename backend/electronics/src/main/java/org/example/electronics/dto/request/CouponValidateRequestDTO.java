package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CouponValidateRequestDTO(
        @NotBlank(message = "Mã coupon không được để trống")
        String code
) {}
