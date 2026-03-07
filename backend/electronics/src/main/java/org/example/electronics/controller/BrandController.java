package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.BrandRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.BrandResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // ==================== Public ====================

    @GetMapping("/api/brands")
    public ResponseEntity<ApiResponse<List<BrandResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(brandService.getAll()));
    }

    // ==================== Admin ====================

    @GetMapping("/api/admin/brands/{id}")
    @RequirePermission("MANAGE_BRAND")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getById(id)));
    }

    @PostMapping("/api/admin/brands")
    @RequirePermission("MANAGE_BRAND")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> create(
            @Valid @RequestBody BrandRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo thương hiệu thành công", brandService.create(request)));
    }

    @PutMapping("/api/admin/brands/{id}")
    @RequirePermission("MANAGE_BRAND")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody BrandRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thương hiệu thành công", brandService.update(id, request)));
    }

    @DeleteMapping("/api/admin/brands/{id}")
    @RequirePermission("MANAGE_BRAND")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        brandService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thương hiệu thành công", null));
    }
}
