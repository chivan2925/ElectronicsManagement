package org.example.electronics.repository;

import org.example.electronics.entity.PaymentTransactionEntity;
import org.example.electronics.entity.enums.PaymentTransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Integer> {
    List<PaymentTransactionEntity> findByOrderId(Integer orderId);
    Optional<PaymentTransactionEntity> findByProviderPaymentId(String providerPaymentId);
    boolean existsByOrderIdAndStatus(Integer orderId, PaymentTransactionStatus status);
}
