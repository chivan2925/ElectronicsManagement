package org.example.electronics.dto.response.admin;

import org.example.electronics.entity.enums.UserStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record AdminRoleResponseDTO(
        Integer id,

        String name,

        Set<AdminPermissionResponseDTO> permissions,

        UserStatus status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
