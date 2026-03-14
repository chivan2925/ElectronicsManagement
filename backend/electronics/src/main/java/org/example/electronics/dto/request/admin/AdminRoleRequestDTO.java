package org.example.electronics.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.electronics.entity.enums.UserStatus;

public record AdminRoleRequestDTO(
        @NotBlank(message = "Tên chức vụ không được để trống")
        String name,

        @NotNull(message = "Trạng thái chức vụ không được null")
        UserStatus status
) {
}
