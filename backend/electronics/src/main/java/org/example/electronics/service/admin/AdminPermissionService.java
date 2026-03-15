package org.example.electronics.service.admin;

import org.example.electronics.dto.request.admin.AdminPermissionRequestDTO;
import org.example.electronics.dto.response.admin.AdminPermissionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AdminPermissionService {
    
    AdminPermissionResponseDTO createPermission(AdminPermissionRequestDTO adminPermissionRequestDTO);
    AdminPermissionResponseDTO updatePermission(Integer permissionId, AdminPermissionRequestDTO adminPermissionRequestDTO);
    Page<AdminPermissionResponseDTO> getAllPermissions(String keyword, LocalDate fromDate, LocalDate toDate, Pageable pageable);
    AdminPermissionResponseDTO getPermissionById(Integer permissionId);
}
