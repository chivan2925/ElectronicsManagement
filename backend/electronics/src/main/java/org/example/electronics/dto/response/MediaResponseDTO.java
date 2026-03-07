package org.example.electronics.dto.response;

import java.time.LocalDateTime;

public record MediaResponseDTO(
        Integer id,
        Integer productId,
        String imageUrl,
        Boolean isPrimary,
        Integer sortOrder,
        LocalDateTime createdAt
) {}
