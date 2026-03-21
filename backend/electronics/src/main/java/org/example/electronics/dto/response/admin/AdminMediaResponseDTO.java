package org.example.electronics.dto.response.admin;

import java.time.LocalDateTime;

public record AdminMediaResponseDTO(
        Integer id,

        String imageUrl,

        Boolean isPrimary,

        Integer displayOrder,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
