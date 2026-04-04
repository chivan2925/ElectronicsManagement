package org.example.electronics.dto.response.admin.order;

import java.math.BigDecimal;

public record AdminOrderItemResponseDTO(

        Integer variantId,

        String productName,

        BigDecimal price,

        Integer quantity
) {
}
