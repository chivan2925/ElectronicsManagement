package org.example.electronics.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.electronics.dto.request.admin.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminUserResponseDTO;
import org.example.electronics.service.admin.impl.AdminUserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@Tag()
public class AdminUserController {

    private final AdminUserServiceImpl adminUserService;

    public AdminUserController(AdminUserServiceImpl adminUserService) {
        this.adminUserService = adminUserService;
    }

    @PatchMapping("/{userId}/status")
    @Operation
    public ResponseEntity<AdminUserResponseDTO> updateUserStatus (
            @PathVariable Integer userId,
            @Valid @RequestBody AdminUpdateUserStatusRequestDTO adminUpdateUserStatusRequestDTO
    ) {
        AdminUserResponseDTO adminUserResponseDTO = adminUserService.updateStatusUser(userId, adminUpdateUserStatusRequestDTO);

        return ResponseEntity.ok(adminUserResponseDTO);
    }

}
