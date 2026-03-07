package org.example.electronics.repository;

import org.example.electronics.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    boolean existsByName(String name);
}
