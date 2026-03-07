package org.example.electronics.dto.response;

import java.time.LocalDateTime;

public record PermissionResponseDTO(
        Integer id,
        String name,
        String description,
        LocalDateTime createdAt
) {}
