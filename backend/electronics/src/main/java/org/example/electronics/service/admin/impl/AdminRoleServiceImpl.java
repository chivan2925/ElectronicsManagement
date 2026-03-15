package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.admin.AdminRoleRequestDTO;
import org.example.electronics.dto.request.admin.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminRoleResponseDTO;
import org.example.electronics.entity.PermissionEntity;
import org.example.electronics.entity.RoleEntity;
import org.example.electronics.entity.enums.UserStatus;
import org.example.electronics.mapper.RoleMapper;
import org.example.electronics.repository.PermissionRepository;
import org.example.electronics.repository.RoleRepository;
import org.example.electronics.repository.StaffRepository;
import org.example.electronics.service.admin.AdminRoleService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final StaffRepository staffRepository;
    private final PermissionRepository permissionRepository;

    public AdminRoleServiceImpl(RoleMapper roleMapper, RoleRepository roleRepository, StaffRepository staffRepository, PermissionRepository permissionRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
        this.staffRepository = staffRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    @Override
    public AdminRoleResponseDTO createRole(AdminRoleRequestDTO adminRoleRequestDTO) {
        if(roleRepository.existsByName(adminRoleRequestDTO.name())) {
            throw new IllegalArgumentException("Tên chức vụ này đã tồn tại");
        }

        RoleEntity newRoleEntity = roleMapper.toEntity(adminRoleRequestDTO);

        List<PermissionEntity> permissionEntityList = permissionRepository.findAllById(adminRoleRequestDTO.permissionIds());
        newRoleEntity.setPermissions(new HashSet<>(permissionEntityList));

        newRoleEntity = roleRepository.save(newRoleEntity);

        return roleMapper.toResponseDTO(newRoleEntity);
    }

    @Transactional
    @Override
    public AdminRoleResponseDTO updateRole(Integer roleId, AdminRoleRequestDTO adminRoleRequestDTO) {
        if(roleRepository.existsByNameAndIdNot(adminRoleRequestDTO.name(), roleId)) {
            throw new IllegalArgumentException("Tên chức vụ này đã bị trùng với một chức vụ khác");
        }
        RoleEntity existingRoleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy chức vụ với id: " + roleId
                ));

        roleMapper.updateEntityFromDTO(adminRoleRequestDTO, existingRoleEntity);

        List<PermissionEntity> permissionEntityList = permissionRepository.findAllById(adminRoleRequestDTO.permissionIds());
        existingRoleEntity.setPermissions(new HashSet<>(permissionEntityList));

        existingRoleEntity = roleRepository.save(existingRoleEntity);

        return roleMapper.toResponseDTO(existingRoleEntity);
    }

    @Transactional
    @Override
    public AdminRoleResponseDTO updateStatusRole(Integer roleId, AdminUpdateUserStatusRequestDTO adminUpdateUserStatusRequestDTO) {
        RoleEntity existingRoleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy chức vụ với id: " + roleId
                ));

        existingRoleEntity.setStatus(adminUpdateUserStatusRequestDTO.status());

        existingRoleEntity = roleRepository.save(existingRoleEntity);

        return roleMapper.toResponseDTO(existingRoleEntity);
    }

    @Transactional
    @Override
    public void deleteRole(Integer roleId) {
        if(staffRepository.countByRole_Id(roleId) > 0) {
            throw new IllegalArgumentException("Không thể xóa vì đang có nhân viên giữ chức vụ này");
        }

        RoleEntity existingRoleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy chức vụ với id: " + roleId
                ));

        existingRoleEntity.setStatus(UserStatus.DELETED);

        roleRepository.save(existingRoleEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminRoleResponseDTO> getAllRoles(String keyword, UserStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<RoleEntity> roleEntityPage = roleRepository.findRolesWithFilter(finalKeyword, status, startDateTime, endDateTime, pageable);

        return roleEntityPage.map(roleMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminRoleResponseDTO getRoleById(Integer roleId) {
        RoleEntity existingRoleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy chức vụ với id: " + roleId
                ));

        return roleMapper.toResponseDTO(existingRoleEntity);
    }
}
