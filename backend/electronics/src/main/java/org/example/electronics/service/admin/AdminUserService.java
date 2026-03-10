package org.example.electronics.service.admin;

import org.example.electronics.dto.request.admin.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminUserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserService {

    AdminUserResponseDTO updateStatusUser(Integer userId, AdminUpdateUserStatusRequestDTO adminUpdateUserStatusRequestDTO);
    void deleteUser(Integer userId);
    Page<AdminUserResponseDTO> getAllUsers(Pageable pageable);
    AdminUserResponseDTO getUserById(Integer userId);
}
