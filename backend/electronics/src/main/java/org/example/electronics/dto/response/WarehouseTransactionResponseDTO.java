package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.WarehouseTransactionStatus;
import org.example.electronics.entity.enums.WarehouseTransactionType;

import java.time.LocalDateTime;
import java.util.List;

public record WarehouseTransactionResponseDTO(
        Integer id,
        Integer warehouseId,
        String warehouseName,
        WarehouseTransactionType type,
        WarehouseTransactionStatus status,
        String note,
        Integer orderId,
        Integer returnRequestId,
        List<WarehouseTransactionItemResponseDTO> items,
        LocalDateTime createdAt
) {}
