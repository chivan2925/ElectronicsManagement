package org.example.electronics.dto.response;

public record WarehouseDetailResponseDTO(
        Integer warehouseId,
        Integer variantId,
        String variantName,
        String productName,
        Integer quantity
) {}
