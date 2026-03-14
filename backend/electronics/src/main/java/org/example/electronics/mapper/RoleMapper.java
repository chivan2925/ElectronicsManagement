package org.example.electronics.mapper;

import org.example.electronics.dto.request.admin.AdminRoleRequestDTO;
import org.example.electronics.dto.response.admin.AdminRoleResponseDTO;
import org.example.electronics.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleEntity toEntity(AdminRoleRequestDTO adminRoleRequestDTO);

    AdminRoleResponseDTO toResponseDTO(RoleEntity roleEntity);

    void updateEntityFromDTO(AdminRoleRequestDTO adminRoleRequestDTO,
                             @MappingTarget RoleEntity roleEntity);
}
