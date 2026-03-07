package org.example.electronics.dto.response;

public record TopProductDTO(
        Integer productId,
        String productName,
        Long totalSold
) {}
