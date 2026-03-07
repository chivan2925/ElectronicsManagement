package org.example.electronics.dto.response;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Integer variantId,
        String productName,
        String variantName,
        String color,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal,
        String productImage
) {}
