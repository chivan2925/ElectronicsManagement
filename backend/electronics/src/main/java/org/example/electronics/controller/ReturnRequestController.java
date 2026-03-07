package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CreateReturnRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.ReturnRequestResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.ReturnRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReturnRequestController {

    private final ReturnRequestService returnService;

    public ReturnRequestController(ReturnRequestService returnService) {
        this.returnService = returnService;
    }

    // ==================== Customer ====================

    @PostMapping("/api/returns")
    public ResponseEntity<ApiResponse<ReturnRequestResponseDTO>> createRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateReturnRequestDTO request) {
        ReturnRequestResponseDTO response = returnService.createRequest(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Tạo yêu cầu đổi trả thành công", response));
    }

    @GetMapping("/api/returns/me")
    public ResponseEntity<ApiResponse<List<ReturnRequestResponseDTO>>> getMyRequests(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success(returnService.getMyRequests(userDetails.getUsername())));
    }

    // ==================== Admin ====================

    @GetMapping("/api/admin/returns")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<List<ReturnRequestResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(returnService.getAll()));
    }

    @PutMapping("/api/admin/returns/{id}/approve")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<ReturnRequestResponseDTO>> approve(
            @PathVariable Integer id,
            @RequestParam(required = false) Integer warehouseId) {
        return ResponseEntity.ok(ApiResponse.success("Duyệt yêu cầu thành công", returnService.approve(id, warehouseId)));
    }

    @PutMapping("/api/admin/returns/{id}/reject")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<ReturnRequestResponseDTO>> reject(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Từ chối yêu cầu thành công", returnService.reject(id)));
    }

    @PutMapping("/api/admin/returns/{id}/cancel")
    @RequirePermission("MANAGE_ORDER")
    public ResponseEntity<ApiResponse<ReturnRequestResponseDTO>> cancel(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Hủy yêu cầu thành công", returnService.cancel(id)));
    }
}
