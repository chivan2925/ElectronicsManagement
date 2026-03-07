package org.example.electronics.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.example.electronics.entity.enums.WarehouseTransactionType;

import java.util.List;

public record WarehouseTransactionRequestDTO(
        @NotNull(message = "Warehouse ID không được để trống")
        Integer warehouseId,

        @NotNull(message = "Loại phiếu không được để trống")
        WarehouseTransactionType type,

        String note,

        Integer orderId,

        Integer returnRequestId,

        @NotEmpty(message = "Danh sách sản phẩm không được trống")
        @Valid
        List<WarehouseTransactionItemDTO> items
) {}
