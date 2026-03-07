package org.example.electronics.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.electronics.entity.enums.ReturnRequestType;

import java.util.List;

public record CreateReturnRequestDTO(
        @NotNull(message = "Order ID không được để trống")
        Integer orderId,

        @NotNull(message = "Loại yêu cầu không được để trống")
        ReturnRequestType type,

        String reason,

        String evidenceJson,

        @NotEmpty(message = "Danh sách sản phẩm không được trống")
        @Valid
        List<ReturnRequestItemDTO> items
) {}
