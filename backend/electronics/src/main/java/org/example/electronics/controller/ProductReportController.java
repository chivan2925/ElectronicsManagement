package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CreateReportRequestDTO;
import org.example.electronics.dto.request.HandleReportRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.ReportResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.ProductReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductReportController {

    private final ProductReportService reportService;

    public ProductReportController(ProductReportService reportService) {
        this.reportService = reportService;
    }

    // --- CUSTOMER ENDPOINTS ---

    @PostMapping("/reports")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> createReport(
            @Valid @RequestBody CreateReportRequestDTO request,
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(
                "Đã gửi báo cáo vi phạm thành công",
                reportService.createReport(authentication.getName(), request)
        ));
    }

    @GetMapping("/reports/me")
    public ResponseEntity<ApiResponse<List<ReportResponseDTO>>> getMyReports(
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(
                "Danh sách báo cáo của tôi",
                reportService.getMyReports(authentication.getName())
        ));
    }

    // --- ADMIN / STAFF ENDPOINTS ---

    @GetMapping("/admin/reports")
    @RequirePermission("MANAGE_REPORT")
    public ResponseEntity<ApiResponse<Page<ReportResponseDTO>>> getAllReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(
                "Lấy danh sách báo cáo vi phạm",
                reportService.getAllReports(PageRequest.of(page, size))
        ));
    }

    @GetMapping("/admin/reports/{id}")
    @RequirePermission("MANAGE_REPORT")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> getReportById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(
                "Chi tiết báo cáo",
                reportService.getReportById(id)
        ));
    }

    @PutMapping("/admin/reports/{id}/handle")
    @RequirePermission("MANAGE_REPORT")
    public ResponseEntity<ApiResponse<ReportResponseDTO>> handleReport(
            @PathVariable Integer id,
            @Valid @RequestBody HandleReportRequestDTO request,
            Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(
                "Xử lý báo cáo thành công",
                reportService.handleReport(id, authentication.getName(), request)
        ));
    }
}
