package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.UserStatus;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Integer id,
        String name,
        String avatar,
        String email,
        String phoneNumber,
        UserStatus status,
        LocalDateTime createdAt
) {}
