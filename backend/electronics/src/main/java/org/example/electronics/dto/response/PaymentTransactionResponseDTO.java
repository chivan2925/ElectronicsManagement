package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.PaymentTransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentTransactionResponseDTO(
        Integer id,
        Integer orderId,
        String provider,
        String providerPaymentId,
        BigDecimal amount,
        PaymentTransactionStatus status,
        LocalDateTime createdAt
) {}
