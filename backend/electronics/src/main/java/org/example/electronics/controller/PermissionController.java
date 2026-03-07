package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.PermissionRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.PermissionResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @RequirePermission("MANAGE_PERMISSION")
    public ResponseEntity<ApiResponse<List<PermissionResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(permissionService.getAll()));
    }

    @GetMapping("/{id}")
    @RequirePermission("MANAGE_PERMISSION")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.getById(id)));
    }

    @PostMapping
    @RequirePermission("MANAGE_PERMISSION")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> create(
            @Valid @RequestBody PermissionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo quyền thành công", permissionService.create(request)));
    }

    @PutMapping("/{id}")
    @RequirePermission("MANAGE_PERMISSION")
    public ResponseEntity<ApiResponse<PermissionResponseDTO>> update(@PathVariable Integer id,
                                                                      @Valid @RequestBody PermissionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật quyền thành công", permissionService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @RequirePermission("MANAGE_PERMISSION")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        permissionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa quyền thành công", null));
    }
}
