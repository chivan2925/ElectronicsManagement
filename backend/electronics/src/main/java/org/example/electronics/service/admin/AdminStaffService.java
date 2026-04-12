package org.example.electronics.service.admin;

import org.example.electronics.dto.request.admin.AdminStaffRequestDTO;
import org.example.electronics.dto.request.admin.status.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminStaffResponseDTO;
import org.example.electronics.entity.enums.DateFilterType;
import org.example.electronics.entity.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface AdminStaffService {
    
    AdminStaffResponseDTO createStaff(AdminStaffRequestDTO adminStaffRequestDTO);
    AdminStaffResponseDTO updateStaff(Integer staffId, AdminStaffRequestDTO adminStaffRequestDTO);
    AdminStaffResponseDTO updateStatusStaff(Integer staffId, AdminUpdateUserStatusRequestDTO adminUpdateUserStatusRequestDTO);
    void deleteStaff(Integer staffId);
    Page<AdminStaffResponseDTO> getAllStaffs(String keyword, UserStatus status, DateFilterType dateType, LocalDate fromDate, LocalDate toDate, Pageable pageable);
    AdminStaffResponseDTO getStaffById(Integer staffId);
}
