package org.example.electronics.dto.response;

public record ReturnRequestItemResponseDTO(
        Integer orderDetailId,
        Integer variantId,
        String variantName,
        String productName,
        Integer quantity
) {}
