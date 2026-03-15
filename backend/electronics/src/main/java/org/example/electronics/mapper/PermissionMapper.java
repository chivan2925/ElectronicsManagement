package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminPermissionRequestDTO;
import org.example.electronics.dto.response.admin.AdminPermissionResponseDTO;
import org.example.electronics.entity.PermissionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {

    PermissionEntity toEntity(AdminPermissionRequestDTO adminPermissionRequestDTO);

    AdminPermissionResponseDTO toResponseDTO(PermissionEntity permissionEntity);

    void updateEntityFromDTO(AdminPermissionRequestDTO adminPermissionRequestDTO,
                             @MappingTarget PermissionEntity permissionEntity);
}
