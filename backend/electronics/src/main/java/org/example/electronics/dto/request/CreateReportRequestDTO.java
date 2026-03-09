package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateReportRequestDTO(
        @NotNull(message = "Product ID là bắt buộc")
        Integer productId,

        @NotBlank(message = "Lý do báo cáo là bắt buộc")
        String reason,

        @NotBlank(message = "Bằng chứng/hình ảnh là bắt buộc")
        String proofJson
) {}
