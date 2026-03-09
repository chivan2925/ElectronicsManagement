package org.example.electronics.dto.response;

import java.time.LocalDateTime;

public record ReportResponseDTO(
        Integer id,
        Integer productId,
        String productName,
        String userName,
        String staffName,
        String reason,
        String proofJson,
        String status,
        String handlingMeasures,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
