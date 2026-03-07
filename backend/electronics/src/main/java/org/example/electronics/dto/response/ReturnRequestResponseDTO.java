package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.ReturnRequestStatus;
import org.example.electronics.entity.enums.ReturnRequestType;

import java.time.LocalDateTime;
import java.util.List;

public record ReturnRequestResponseDTO(
        Integer id,
        Integer orderId,
        String orderCode,
        ReturnRequestType type,
        ReturnRequestStatus status,
        String reason,
        String evidenceJson,
        List<ReturnRequestItemResponseDTO> items,
        LocalDateTime createdAt
) {}
