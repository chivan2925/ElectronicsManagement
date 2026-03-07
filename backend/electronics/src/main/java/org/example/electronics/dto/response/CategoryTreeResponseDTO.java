package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryTreeResponseDTO(
        Integer id,
        String name,
        String iconUrl,
        String slug,
        ProductStatus status,
        List<CategoryTreeResponseDTO> children
) {}
