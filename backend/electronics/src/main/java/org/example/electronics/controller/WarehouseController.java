package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.WarehouseRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.WarehouseDetailResponseDTO;
import org.example.electronics.dto.response.WarehouseResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.WarehouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @GetMapping
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<List<WarehouseResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getAll()));
    }

    @GetMapping("/{id}")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<WarehouseResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getById(id)));
    }

    @PostMapping
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<WarehouseResponseDTO>> create(
            @Valid @RequestBody WarehouseRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo kho thành công", warehouseService.create(request)));
    }

    @PutMapping("/{id}")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<WarehouseResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody WarehouseRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật kho thành công", warehouseService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        warehouseService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa kho thành công", null));
    }

    @GetMapping("/{id}/stock")
    @RequirePermission("MANAGE_WAREHOUSE")
    public ResponseEntity<ApiResponse<List<WarehouseDetailResponseDTO>>> getStock(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(warehouseService.getStock(id)));
    }
}
