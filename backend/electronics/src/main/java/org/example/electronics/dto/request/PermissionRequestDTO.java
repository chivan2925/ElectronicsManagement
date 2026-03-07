package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PermissionRequestDTO(
        @NotBlank(message = "Tên quyền không được để trống")
        String name,

        String description
) {}
