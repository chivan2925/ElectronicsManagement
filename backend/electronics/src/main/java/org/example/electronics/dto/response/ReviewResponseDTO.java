package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.ReviewStatus;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        Integer id,
        Integer productId,
        String productName,
        String userEmail,
        String userName,
        Integer star,
        String content,
        ReviewStatus status,
        LocalDateTime createdAt
) {}
