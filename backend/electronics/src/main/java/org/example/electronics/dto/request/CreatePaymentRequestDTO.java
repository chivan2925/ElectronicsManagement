package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequestDTO(
        @NotNull(message = "Order ID không được để trống")
        Integer orderId,

        String provider  // VNPAY, MOMO — defaults to VNPAY if null
) {}
