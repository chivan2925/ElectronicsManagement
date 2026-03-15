package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.admin.AdminPermissionRequestDTO;
import org.example.electronics.dto.response.admin.AdminPermissionResponseDTO;
import org.example.electronics.entity.PermissionEntity;
import org.example.electronics.mapper.PermissionMapper;
import org.example.electronics.repository.PermissionRepository;
import org.example.electronics.service.admin.AdminPermissionService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminPermissionServiceImpl implements AdminPermissionService {

    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    public AdminPermissionServiceImpl(PermissionMapper permissionMapper, PermissionRepository permissionRepository) {
        this.permissionMapper = permissionMapper;
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    @Override
    public AdminPermissionResponseDTO createPermission(AdminPermissionRequestDTO adminPermissionRequestDTO) {
        if(permissionRepository.existsByCodeOrName(adminPermissionRequestDTO.code(), adminPermissionRequestDTO.name())) {
            throw new IllegalArgumentException("Code hoặc tên của quyền hạn đã tồn tại");
        }

        PermissionEntity permissionEntity = permissionMapper.toEntity(adminPermissionRequestDTO);

        permissionEntity = permissionRepository.save(permissionEntity);

        return permissionMapper.toResponseDTO(permissionEntity);
    }

    @Transactional
    @Override
    public AdminPermissionResponseDTO updatePermission(Integer permissionId, AdminPermissionRequestDTO adminPermissionRequestDTO) {
        if(permissionRepository.existsByCodeAndIdNot(adminPermissionRequestDTO.code(), permissionId) ||
            permissionRepository.existsByNameAndIdNot(adminPermissionRequestDTO.name(), permissionId)) {
            throw new IllegalArgumentException("Code hoặc tên của quyền hạn đã bị trùng với quyền hạn khác");
        }

        PermissionEntity existingPermissionEntity = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy quyền hạn với id: " + permissionId
                ));

        permissionMapper.updateEntityFromDTO(adminPermissionRequestDTO, existingPermissionEntity);

        existingPermissionEntity = permissionRepository.save(existingPermissionEntity);

        return permissionMapper.toResponseDTO(existingPermissionEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminPermissionResponseDTO> getAllPermissions(String keyword, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<PermissionEntity> permissionEntityPage = permissionRepository.findPermissionsWithFilter(finalKeyword, startDateTime, endDateTime, pageable);

        return permissionEntityPage.map(permissionMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminPermissionResponseDTO getPermissionById(Integer permissionId) {
        PermissionEntity existingPermissionEntity = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy quyền hạn với id: " + permissionId
                ));

        return permissionMapper.toResponseDTO(existingPermissionEntity);
    }
}
