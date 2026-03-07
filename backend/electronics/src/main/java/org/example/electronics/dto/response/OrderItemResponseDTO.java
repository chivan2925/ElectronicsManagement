package org.example.electronics.dto.response;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Integer variantId,
        String productName,
        String variantName,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal
) {}
