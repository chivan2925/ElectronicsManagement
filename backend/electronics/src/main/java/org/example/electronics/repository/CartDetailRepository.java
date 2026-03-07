package org.example.electronics.repository;

import org.example.electronics.entity.CartDetailEntity;
import org.example.electronics.entity.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetailEntity, CartDetailId> {
    List<CartDetailEntity> findByCartId(Integer cartId);
    Optional<CartDetailEntity> findByCartIdAndVariantId(Integer cartId, Integer variantId);
    void deleteByCartIdAndVariantId(Integer cartId, Integer variantId);
    void deleteByCartId(Integer cartId);
}
