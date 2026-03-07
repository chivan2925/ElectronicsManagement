package org.example.electronics.controller;

import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.LowStockDTO;
import org.example.electronics.dto.response.RevenueReportDTO;
import org.example.electronics.dto.response.TopProductDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.ReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/revenue")
    @RequirePermission("MANAGE_ORDER") // Any high-level admin permission works
    public ResponseEntity<ApiResponse<List<RevenueReportDTO>>> getRevenue(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getRevenue(startDate, endDate)));
    }

    @GetMapping("/top-products")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<List<TopProductDTO>>> getTopProducts(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getTopSellingProducts(limit)));
    }

    @GetMapping("/low-stock")
    @RequirePermission("MANAGE_PRODUCT") // Or MANAGE_WAREHOUSE
    public ResponseEntity<ApiResponse<List<LowStockDTO>>> getLowStock(
            @RequestParam(defaultValue = "10") int threshold) {
        return ResponseEntity.ok(ApiResponse.success(reportService.getLowStockVariants(threshold)));
    }
}
