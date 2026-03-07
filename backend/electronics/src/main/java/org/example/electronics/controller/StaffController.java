package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.StaffRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.StaffResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/staffs")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    @RequirePermission("MANAGE_STAFF")
    public ResponseEntity<ApiResponse<List<StaffResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(staffService.getAll()));
    }

    @GetMapping("/{id}")
    @RequirePermission("MANAGE_STAFF")
    public ResponseEntity<ApiResponse<StaffResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(staffService.getById(id)));
    }

    @PostMapping
    @RequirePermission("MANAGE_STAFF")
    public ResponseEntity<ApiResponse<StaffResponseDTO>> create(@Valid @RequestBody StaffRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo nhân viên thành công", staffService.create(request)));
    }

    @PutMapping("/{id}")
    @RequirePermission("MANAGE_STAFF")
    public ResponseEntity<ApiResponse<StaffResponseDTO>> update(@PathVariable Integer id,
                                                                 @Valid @RequestBody StaffRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật nhân viên thành công", staffService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @RequirePermission("MANAGE_STAFF")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        staffService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa nhân viên thành công", null));
    }

    @PutMapping("/{id}/role/{roleId}")
    @RequirePermission("MANAGE_STAFF")
    public ResponseEntity<ApiResponse<StaffResponseDTO>> assignRole(@PathVariable Integer id,
                                                                     @PathVariable Integer roleId) {
        return ResponseEntity.ok(ApiResponse.success("Gán vai trò thành công", staffService.assignRole(id, roleId)));
    }
}
