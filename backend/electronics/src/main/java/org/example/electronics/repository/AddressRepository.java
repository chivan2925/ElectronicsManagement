package org.example.electronics.repository;

import org.example.electronics.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {
    List<AddressEntity> findByUserId(Integer userId);
    Optional<AddressEntity> findByIdAndUserId(Integer id, Integer userId);

    @Modifying
    @Query("UPDATE AddressEntity a SET a.isDefault = false WHERE a.user.id = :userId AND a.isDefault = true")
    void clearDefaultByUserId(Integer userId);
}
