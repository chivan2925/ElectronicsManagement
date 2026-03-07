package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.WarehouseTransactionRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.WarehouseTransactionResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.WarehouseTransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouse-transactions")
public class WarehouseTransactionController {

    private final WarehouseTransactionService transactionService;

    public WarehouseTransactionController(WarehouseTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<List<WarehouseTransactionResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(transactionService.getAll()));
    }

    @GetMapping("/{id}")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<WarehouseTransactionResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.getById(id)));
    }

    @PostMapping
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<WarehouseTransactionResponseDTO>> create(
            @Valid @RequestBody WarehouseTransactionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo phiếu thành công", transactionService.create(request)));
    }

    @PutMapping("/{id}/approve")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<WarehouseTransactionResponseDTO>> approve(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Duyệt phiếu thành công", transactionService.approve(id)));
    }

    @PutMapping("/{id}/reject")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<WarehouseTransactionResponseDTO>> reject(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Từ chối phiếu thành công", transactionService.reject(id)));
    }

    @GetMapping("/warehouse/{warehouseId}")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<List<WarehouseTransactionResponseDTO>>> getByWarehouse(
            @PathVariable Integer warehouseId) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.getByWarehouseId(warehouseId)));
    }
}
