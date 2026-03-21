package org.example.electronics.dto.request.admin.media;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AdminCreateMediaRequestDTO(

        @NotBlank(message = "Đường dẫn hình ảnh media không được để trống")
        String imageUrl,

        @NotNull(message = "isPrimary không được null")
        Boolean isPrimary,

        @NotNull(message = "Thứ tự hiển thị media không được null")
        @PositiveOrZero
        Integer displayOrder
) {
}
