package org.example.electronics.dto.response;

public record WarehouseTransactionItemResponseDTO(
        Integer variantId,
        String variantName,
        String productName,
        Integer quantity
) {}
