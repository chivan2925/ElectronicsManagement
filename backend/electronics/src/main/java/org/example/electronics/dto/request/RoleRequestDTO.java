package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoleRequestDTO(
        @NotBlank(message = "Tên vai trò không được để trống")
        String name
) {}
