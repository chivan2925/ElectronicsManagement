package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ProductRequestDTO(
        @NotBlank(message = "Tên sản phẩm không được để trống")
        String name,

        @NotBlank(message = "Slug không được để trống")
        String slug,

        Integer categoryId,

        Integer brandId,

        String description
) {}
