package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.AssignPermissionRequestDTO;
import org.example.electronics.dto.request.RoleRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.RoleResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @RequirePermission("MANAGE_ROLE")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(roleService.getAll()));
    }

    @GetMapping("/{id}")
    @RequirePermission("MANAGE_ROLE")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.getById(id)));
    }

    @PostMapping
    @RequirePermission("MANAGE_ROLE")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> create(@Valid @RequestBody RoleRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo vai trò thành công", roleService.create(request)));
    }

    @PutMapping("/{id}")
    @RequirePermission("MANAGE_ROLE")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> update(@PathVariable Integer id,
                                                                @Valid @RequestBody RoleRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật vai trò thành công", roleService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @RequirePermission("MANAGE_ROLE")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        roleService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa vai trò thành công", null));
    }

    @PostMapping("/{id}/permissions")
    @RequirePermission("MANAGE_ROLE")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> assignPermission(
            @PathVariable Integer id,
            @RequestBody AssignPermissionRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Gán quyền thành công", roleService.assignPermission(id, request)));
    }

    @DeleteMapping("/{id}/permissions/{permissionId}")
    @RequirePermission("MANAGE_ROLE")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> removePermission(
            @PathVariable Integer id,
            @PathVariable Integer permissionId) {
        return ResponseEntity.ok(ApiResponse.success("Xóa quyền thành công", roleService.removePermission(id, permissionId)));
    }
}
