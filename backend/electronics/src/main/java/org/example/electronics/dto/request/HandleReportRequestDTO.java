package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotNull;
import org.example.electronics.entity.enums.HandlingMeasureType;
import org.example.electronics.entity.enums.ReportStatus;

public record HandleReportRequestDTO(
        @NotNull(message = "Trạng thái mới là bắt buộc")
        ReportStatus status,

        @NotNull(message = "Biện pháp xử lý là bắt buộc")
        HandlingMeasureType handlingMeasures
) {}
