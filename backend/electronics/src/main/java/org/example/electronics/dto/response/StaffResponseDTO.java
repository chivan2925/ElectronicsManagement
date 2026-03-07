package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.StaffStatus;

import java.time.LocalDateTime;

public record StaffResponseDTO(
        Integer id,
        String name,
        String avatar,
        String email,
        String phoneNumber,
        String address,
        String roleName,
        StaffStatus status,
        LocalDateTime assignedAt,
        LocalDateTime createdAt
) {}
