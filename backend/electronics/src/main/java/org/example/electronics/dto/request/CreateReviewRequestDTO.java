package org.example.electronics.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReviewRequestDTO(
        @NotNull(message = "Product ID không được để trống")
        Integer productId,

        @NotNull(message = "Số sao đánh giá không được để trống")
        @Min(value = 1, message = "Đánh giá thấp nhất là 1 sao")
        @Max(value = 5, message = "Đánh giá cao nhất là 5 sao")
        Integer star,

        String content
) {}
