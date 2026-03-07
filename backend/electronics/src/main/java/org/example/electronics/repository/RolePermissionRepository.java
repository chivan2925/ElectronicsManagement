package org.example.electronics.repository;

import org.example.electronics.entity.RolePermissionEntity;
import org.example.electronics.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, RolePermissionId> {
    List<RolePermissionEntity> findByRoleId(Integer roleId);
    void deleteByRoleIdAndPermissionId(Integer roleId, Integer permissionId);
}
