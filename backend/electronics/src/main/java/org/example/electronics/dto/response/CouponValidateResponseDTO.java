package org.example.electronics.dto.response;

import java.math.BigDecimal;

public record CouponValidateResponseDTO(
        boolean valid,
        String code,
        String message,
        BigDecimal discountAmount
) {}
