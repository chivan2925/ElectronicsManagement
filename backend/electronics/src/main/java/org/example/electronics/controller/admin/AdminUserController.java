package org.example.electronics.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.electronics.dto.request.admin.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminUserResponseDTO;
import org.example.electronics.service.admin.AdminUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "2. User Management", description = "Các API dành cho Admin để quản lý người dùng")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PatchMapping("/{userId}/status")
    @Operation(summary = "Cập nhật trạng thái User", description = "Dùng để Khóa (BLOCKED) hoặc Mở khóa (ACTIVE) tài khoản khách hàng.")
    public ResponseEntity<AdminUserResponseDTO> updateUserStatus (
            @PathVariable Integer userId,
            @Valid @RequestBody AdminUpdateUserStatusRequestDTO adminUpdateUserStatusRequestDTO
    ) {
        AdminUserResponseDTO adminUserResponseDTO = adminUserService.updateStatusUser(userId, adminUpdateUserStatusRequestDTO);

        return ResponseEntity.ok(adminUserResponseDTO);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Xóa User (Soft Delete)", description = "Chuyển trạng thái của User thành DELETED.")
    public ResponseEntity<Void> deleteUser (
            @PathVariable Integer userId
    ) {
        adminUserService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Lấy danh sách User", description = "Lấy danh sách người dùng có phân trang. Mặc định sắp xếp ngày tạo mới nhất lên đầu.")
    public ResponseEntity<Page<AdminUserResponseDTO>> getAllUsers(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<AdminUserResponseDTO> adminUserResponseDTOPage = adminUserService.getAllUsers(pageable);

        return ResponseEntity.ok(adminUserResponseDTOPage);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Xem chi tiết 1 User", description = "Lấy toàn bộ thông tin công khai của User dựa vào ID.")
    public ResponseEntity<AdminUserResponseDTO> getUserById(
            @PathVariable Integer userId
    ) {
        AdminUserResponseDTO adminUserResponseDTO = adminUserService.getUserById(userId);

        return ResponseEntity.ok(adminUserResponseDTO);
    }
}
