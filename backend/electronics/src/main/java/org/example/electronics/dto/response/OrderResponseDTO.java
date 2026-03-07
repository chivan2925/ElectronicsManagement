package org.example.electronics.dto.response;

import org.example.electronics.entity.enums.OrderStatus;
import org.example.electronics.entity.enums.PaymentMethodType;
import org.example.electronics.entity.enums.PaymentStatus;
import org.example.electronics.entity.enums.ShippingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Integer id,
        String code,
        String address,
        OrderStatus status,
        PaymentMethodType paymentMethod,
        PaymentStatus paymentStatus,
        BigDecimal discount,
        BigDecimal shippingFee,
        BigDecimal subtotal,
        BigDecimal total,
        String note,
        String shippingProvider,
        ShippingStatus shippingStatus,
        String trackingCode,
        List<OrderItemResponseDTO> items,
        LocalDateTime createdAt
) {}
