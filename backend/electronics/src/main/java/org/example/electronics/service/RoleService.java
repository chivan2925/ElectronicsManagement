package org.example.electronics.service;

import org.example.electronics.dto.request.AssignPermissionRequestDTO;
import org.example.electronics.dto.request.RoleRequestDTO;
import org.example.electronics.dto.response.RoleResponseDTO;
import org.example.electronics.entity.PermissionEntity;
import org.example.electronics.entity.RoleEntity;
import org.example.electronics.entity.RolePermissionEntity;
import org.example.electronics.repository.PermissionRepository;
import org.example.electronics.repository.RolePermissionRepository;
import org.example.electronics.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository,
                       RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public List<RoleResponseDTO> getAll() {
        return roleRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public RoleResponseDTO getById(Integer id) {
        RoleEntity role = findById(id);
        return toResponseDTO(role);
    }

    public RoleResponseDTO create(RoleRequestDTO request) {
        if (roleRepository.existsByName(request.name())) {
            throw new RuntimeException("Tên vai trò đã tồn tại");
        }
        RoleEntity role = RoleEntity.builder().name(request.name()).build();
        roleRepository.save(role);
        return toResponseDTO(role);
    }

    public RoleResponseDTO update(Integer id, RoleRequestDTO request) {
        RoleEntity role = findById(id);
        role.setName(request.name());
        roleRepository.save(role);
        return toResponseDTO(role);
    }

    public void delete(Integer id) {
        RoleEntity role = findById(id);
        roleRepository.delete(role);
    }

    @Transactional
    public RoleResponseDTO assignPermission(Integer roleId, AssignPermissionRequestDTO request) {
        RoleEntity role = findById(roleId);
        PermissionEntity permission = permissionRepository.findById(request.permissionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền"));

        RolePermissionEntity rp = RolePermissionEntity.builder()
                .role(role)
                .permission(permission)
                .build();
        rolePermissionRepository.save(rp);

        // Reload to get updated list
        RoleEntity updated = findById(roleId);
        return toResponseDTO(updated);
    }

    @Transactional
    public RoleResponseDTO removePermission(Integer roleId, Integer permissionId) {
        rolePermissionRepository.deleteByRoleIdAndPermissionId(roleId, permissionId);
        RoleEntity updated = findById(roleId);
        return toResponseDTO(updated);
    }

    private RoleEntity findById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vai trò"));
    }

    private RoleResponseDTO toResponseDTO(RoleEntity role) {
        List<String> permissions = role.getRolePermissions() != null
                ? role.getRolePermissions().stream()
                    .map(rp -> rp.getPermission().getName())
                    .toList()
                : List.of();

        return new RoleResponseDTO(role.getId(), role.getName(), permissions, role.getCreatedAt());
    }
}
