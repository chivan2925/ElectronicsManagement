package org.example.electronics.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RevenueReportDTO(
        LocalDate date,
        BigDecimal totalRevenue,
        Long totalOrders
) {}
