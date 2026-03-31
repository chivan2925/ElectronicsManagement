package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.admin.AdminStaffRequestDTO;
import org.example.electronics.dto.request.admin.AdminUpdateUserStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminStaffResponseDTO;
import org.example.electronics.entity.RoleEntity;
import org.example.electronics.entity.StaffEntity;
import org.example.electronics.entity.enums.UserStatus;
import org.example.electronics.mapper.StaffMapper;
import org.example.electronics.repository.RoleRepository;
import org.example.electronics.repository.StaffRepository;
import org.example.electronics.service.admin.AdminStaffService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AdminStaffServiceImpl implements AdminStaffService {

    private final StaffMapper staffMapper;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;

    public AdminStaffServiceImpl(StaffMapper staffMapper, StaffRepository staffRepository, RoleRepository roleRepository) {
        this.staffMapper = staffMapper;
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public AdminStaffResponseDTO createStaff(AdminStaffRequestDTO adminStaffRequestDTO) {
        if(staffRepository.existsByUsername(adminStaffRequestDTO.username()) ||
            staffRepository.existsByEmail(adminStaffRequestDTO.email()) ||
            staffRepository.existsByPhoneNumber(adminStaffRequestDTO.phoneNumber())) {

            throw new IllegalArgumentException("Username, email hoặc số điện thoại này đã tồn tại");
        }

        RoleEntity roleEntity = roleRepository.findById(adminStaffRequestDTO.roleId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy Chức vụ với ID: " + adminStaffRequestDTO.roleId()
                ));

        StaffEntity newStaffEntity = staffMapper.toEntity(adminStaffRequestDTO);

        newStaffEntity.setRole(roleEntity);
        newStaffEntity.setHashedPassword("Chưa có BCrypt" + adminStaffRequestDTO.password());

        newStaffEntity = staffRepository.save(newStaffEntity);

        return staffMapper.toResponseDTO(newStaffEntity);
    }

    @Transactional
    @Override
    public AdminStaffResponseDTO updateStaff(Integer staffId, AdminStaffRequestDTO adminStaffRequestDTO) {
        if(staffRepository.existsByUsernameAndIdNot(adminStaffRequestDTO.username(), staffId) ||
             staffRepository.existsByEmailAndIdNot(adminStaffRequestDTO.email(), staffId) ||
             staffRepository.existsByPhoneNumberAndIdNot(adminStaffRequestDTO.phoneNumber(), staffId)) {

            throw new IllegalArgumentException("Username, email hoặc số điện thoại này đã bị trùng với một nhân viên khác");
        }

        StaffEntity existingStaffEntity = staffRepository.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy nhân viên với id: " + staffId
                ));

        RoleEntity newRoleEntity = roleRepository.findById(adminStaffRequestDTO.roleId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy Chức vụ với ID: " + adminStaffRequestDTO.roleId()
                ));

        staffMapper.updateEntityFromDTO(adminStaffRequestDTO, existingStaffEntity);

        existingStaffEntity.setRole(newRoleEntity);
        existingStaffEntity.setHashedPassword("Chưa có BCrypt" + adminStaffRequestDTO.password());

        return staffMapper.toResponseDTO(existingStaffEntity);
    }

    @Transactional
    @Override
    public AdminStaffResponseDTO updateStatusStaff(Integer staffId, AdminUpdateUserStatusRequestDTO adminUpdateUserStatusRequestDTO) {
        StaffEntity existingStaffEntity = staffRepository.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy nhân viên với id: " + staffId
                ));

        existingStaffEntity.setStatus(adminUpdateUserStatusRequestDTO.status());

        return staffMapper.toResponseDTO(existingStaffEntity);
    }

    @Transactional
    @Override
    public void deleteStaff(Integer staffId) {
        StaffEntity existingStaffEntity = staffRepository.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy nhân viên với id: " + staffId
                ));

        existingStaffEntity.setStatus(UserStatus.DELETED);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminStaffResponseDTO> getAllStaffs(String keyword, UserStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<StaffEntity> staffEntityPage = staffRepository.findStaffsWithFilter(finalKeyword, status, startDateTime, endDateTime, pageable);

        return staffEntityPage.map(staffMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminStaffResponseDTO getStaffById(Integer staffId) {
        StaffEntity existingStaffEntity = staffRepository.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy nhân viên với id: " + staffId
                ));

        return staffMapper.toResponseDTO(existingStaffEntity);
    }
}
