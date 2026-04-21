package org.example.electronics.repository;

import org.example.electronics.entity.InvalidatedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedTokenEntity, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM InvalidatedTokenEntity t WHERE t.expiryDate <= : now")
    void deleteAllExpiredTokens(Date now);
}
