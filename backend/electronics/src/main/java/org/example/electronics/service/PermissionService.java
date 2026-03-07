package org.example.electronics.service;

import org.example.electronics.dto.request.PermissionRequestDTO;
import org.example.electronics.dto.response.PermissionResponseDTO;
import org.example.electronics.entity.PermissionEntity;
import org.example.electronics.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionResponseDTO> getAll() {
        return permissionRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    public PermissionResponseDTO getById(Integer id) {
        return toResponseDTO(findById(id));
    }

    public PermissionResponseDTO create(PermissionRequestDTO request) {
        if (permissionRepository.existsByName(request.name())) {
            throw new RuntimeException("Tên quyền đã tồn tại");
        }
        PermissionEntity permission = PermissionEntity.builder()
                .name(request.name())
                .description(request.description())
                .build();
        permissionRepository.save(permission);
        return toResponseDTO(permission);
    }

    public PermissionResponseDTO update(Integer id, PermissionRequestDTO request) {
        PermissionEntity permission = findById(id);
        permission.setName(request.name());
        permission.setDescription(request.description());
        permissionRepository.save(permission);
        return toResponseDTO(permission);
    }

    public void delete(Integer id) {
        PermissionEntity permission = findById(id);
        permissionRepository.delete(permission);
    }

    private PermissionEntity findById(Integer id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy quyền"));
    }

    private PermissionResponseDTO toResponseDTO(PermissionEntity p) {
        return new PermissionResponseDTO(p.getId(), p.getName(), p.getDescription(), p.getCreatedAt());
    }
}
