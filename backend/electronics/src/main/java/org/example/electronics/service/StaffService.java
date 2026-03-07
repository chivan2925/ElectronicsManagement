package org.example.electronics.service;

import org.example.electronics.dto.request.StaffRequestDTO;
import org.example.electronics.dto.response.StaffResponseDTO;
import org.example.electronics.entity.RoleEntity;
import org.example.electronics.entity.StaffEntity;
import org.example.electronics.repository.RoleRepository;
import org.example.electronics.repository.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StaffService(StaffRepository staffRepository,
                        RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder) {
        this.staffRepository = staffRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<StaffResponseDTO> getAll() {
        return staffRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public StaffResponseDTO getById(Integer id) {
        return toResponseDTO(findById(id));
    }

    public StaffResponseDTO create(StaffRequestDTO request) {
        if (staffRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email nhân viên đã tồn tại");
        }

        StaffEntity staff = StaffEntity.builder()
                .name(request.name())
                .email(request.email())
                .hashedPassword(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .address(request.address())
                .avatar(request.avatar())
                .build();

        if (request.roleId() != null) {
            RoleEntity role = roleRepository.findById(request.roleId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
            staff.setRole(role);
            staff.setAssignedAt(LocalDateTime.now());
        }

        staffRepository.save(staff);
        return toResponseDTO(staff);
    }

    public StaffResponseDTO update(Integer id, StaffRequestDTO request) {
        StaffEntity staff = findById(id);
        staff.setName(request.name());
        staff.setPhoneNumber(request.phoneNumber());
        staff.setAddress(request.address());
        staff.setAvatar(request.avatar());

        if (request.roleId() != null) {
            RoleEntity role = roleRepository.findById(request.roleId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
            staff.setRole(role);
            staff.setAssignedAt(LocalDateTime.now());
        }

        staffRepository.save(staff);
        return toResponseDTO(staff);
    }

    public void delete(Integer id) {
        StaffEntity staff = findById(id);
        staffRepository.delete(staff);
    }

    public StaffResponseDTO assignRole(Integer staffId, Integer roleId) {
        StaffEntity staff = findById(staffId);
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
        staff.setRole(role);
        staff.setAssignedAt(LocalDateTime.now());
        staffRepository.save(staff);
        return toResponseDTO(staff);
    }

    private StaffEntity findById(Integer id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    private StaffResponseDTO toResponseDTO(StaffEntity staff) {
        return new StaffResponseDTO(
                staff.getId(),
                staff.getName(),
                staff.getAvatar(),
                staff.getEmail(),
                staff.getPhoneNumber(),
                staff.getAddress(),
                staff.getRole() != null ? staff.getRole().getName() : null,
                staff.getStatus(),
                staff.getAssignedAt(),
                staff.getCreatedAt()
        );
    }
}
