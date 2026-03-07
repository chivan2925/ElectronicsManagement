package org.example.electronics.dto.response;

public record PaymentCreateResponseDTO(
        Integer transactionId,
        String paymentUrl,
        String provider,
        String message
) {}
