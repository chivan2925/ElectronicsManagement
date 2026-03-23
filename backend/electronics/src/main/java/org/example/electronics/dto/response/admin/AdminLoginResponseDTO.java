package org.example.electronics.dto.response.admin;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdminLoginResponseDTO(

        @Schema(description = "Chuỗi JWT Token dùng để xác thực các request tiếp theo")
        String accessToken,

        @Schema(description = "Loại token (Thường là Bearer)", example = "Bearer")
        String tokenType,

        Integer staffId,

        String fullName,

        String email,

        String role
) {
        public AdminLoginResponseDTO(String accessToken, Integer staffId, String fullName, String email, String role) {
                this(accessToken, "Bearer", staffId, fullName, email, role);
        }
}
