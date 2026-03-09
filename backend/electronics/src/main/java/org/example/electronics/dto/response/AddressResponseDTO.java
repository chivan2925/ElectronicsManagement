package org.example.electronics.dto.response;

import java.time.LocalDateTime;

public record AddressResponseDTO(
        Integer id,
        Integer userId,
        String label,
        String line,
        String ward,
        String district,
        String province,
        String note,
        Boolean defaultAddress,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
