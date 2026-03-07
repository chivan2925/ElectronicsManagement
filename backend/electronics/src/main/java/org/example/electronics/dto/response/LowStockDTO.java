package org.example.electronics.dto.response;

public record LowStockDTO(
        Integer variantId,
        String variantName,
        Integer productId,
        String productName,
        Integer currentStock
) {}
