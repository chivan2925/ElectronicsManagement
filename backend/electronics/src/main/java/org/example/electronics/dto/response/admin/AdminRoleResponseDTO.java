package org.example.electronics.dto.response.admin;

import org.example.electronics.entity.enums.UserStatus;

import java.time.LocalDateTime;

public record AdminRoleResponseDTO(
        Integer id,
        String name,
        UserStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
