package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.electronics.entity.enums.PaymentMethodType;

public record CheckoutRequestDTO(
        @NotNull(message = "Địa chỉ giao hàng không được để trống")
        Integer addressId,

        @NotNull(message = "Phương thức thanh toán không được để trống")
        PaymentMethodType paymentMethod,

        String couponCode,

        String note
) {}
