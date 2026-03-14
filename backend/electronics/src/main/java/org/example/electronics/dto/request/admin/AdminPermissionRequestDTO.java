package org.example.electronics.dto.request.admin;

import jakarta.validation.constraints.NotBlank;

public record AdminPermissionRequestDTO(
        @NotBlank(message = "Code quyền hạn không được để trống")
        String code,

        @NotBlank(message = "Tên quyền hạn không được để trống")
        String name,

        String description
) {
}
