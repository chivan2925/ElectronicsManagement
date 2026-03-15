package org.example.electronics.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.electronics.dto.request.admin.AdminPermissionRequestDTO;
import org.example.electronics.dto.response.admin.AdminPermissionResponseDTO;
import org.example.electronics.service.admin.AdminPermissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/permissions")
@Tag(
        name = "4. Permission Management",
        description = "Các API dành cho Admin để quản lý danh mục Quyền hạn (Lưu ý: Không có API Xóa vì quyền hạn gắn liền với Source Code)"
)
public class AdminPermissionController {

    private final AdminPermissionService adminPermissionService;

    public AdminPermissionController(AdminPermissionService adminPermissionService) {
        this.adminPermissionService = adminPermissionService;
    }

    @PostMapping
    @Operation(
            summary = "Tạo mới quyền hạn",
            description = "Thêm một mã quyền hạn mới vào hệ thống (VD: PRODUCT_VIEW, ORDER_UPDATE). Code và Tên quyền bắt buộc phải là duy nhất."
    )
    public ResponseEntity<AdminPermissionResponseDTO> createPermission(
            @Valid @RequestBody AdminPermissionRequestDTO adminPermissionRequestDTO
    ) {
        AdminPermissionResponseDTO adminPermissionResponseDTO = adminPermissionService.createPermission(adminPermissionRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(adminPermissionResponseDTO);
    }

    @PutMapping("/{permissionId}")
    @Operation(
            summary = "Cập nhật quyền hạn",
            description = "Chỉnh sửa thông tin của một quyền hạn (Code, Tên, Mô tả). Hệ thống sẽ tự động validate để đảm bảo không bị trùng lặp với các quyền đã có."
    )
    public ResponseEntity<AdminPermissionResponseDTO> updatePermission(
            @PathVariable Integer permissionId,
            @Valid @RequestBody AdminPermissionRequestDTO adminPermissionRequestDTO
    ) {
        AdminPermissionResponseDTO adminPermissionResponseDTO = adminPermissionService.updatePermission(permissionId, adminPermissionRequestDTO);

        return ResponseEntity.ok(adminPermissionResponseDTO);
    }

    @GetMapping
    @Operation(
            summary = "Lấy danh sách quyền hạn (Có phân trang & Lọc)",
            description = "Truy xuất danh sách toàn bộ quyền hạn. Hỗ trợ tìm kiếm theo ID, Code, Tên và lọc theo khoảng thời gian tạo."
    )
    public ResponseEntity<Page<AdminPermissionResponseDTO>> getAllPermissions(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<AdminPermissionResponseDTO> adminPermissionResponseDTOPage = adminPermissionService.getAllPermissions(keyword, fromDate, toDate, pageable);

        return ResponseEntity.ok(adminPermissionResponseDTOPage);
    }

    @GetMapping("/{permissionId}")
    @Operation(
            summary = "Xem chi tiết 1 quyền hạn",
            description = "Lấy toàn bộ thông tin chi tiết của một quyền hạn cụ thể dựa vào ID."
    )
    public ResponseEntity<AdminPermissionResponseDTO> getPermissionById(
            @PathVariable Integer permissionId
    ) {
        AdminPermissionResponseDTO adminPermissionResponseDTO = adminPermissionService.getPermissionById(permissionId);

        return ResponseEntity.ok(adminPermissionResponseDTO);
    }
}
