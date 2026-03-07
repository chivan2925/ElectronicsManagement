package org.example.electronics.dto.request;

import org.example.electronics.entity.enums.ShippingStatus;

public record ShippingRequestDTO(
        String shippingProvider,
        String trackingCode,
        ShippingStatus shippingStatus
) {}
