package org.example.electronics.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record RoleResponseDTO(
        Integer id,
        String name,
        List<String> permissions,
        LocalDateTime createdAt
) {}
