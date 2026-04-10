package org.example.electronics.dto.request.webhook.payment;

import lombok.Builder;

@Builder
public record MomoIpnRequestDTO (

        String partnerCode,
        String orderId,
        String requestId,
        Long amount,
        String orderInfo,
        String orderType,
        Long transId,
        Integer resultCode,
        String message,
        String payType,
        Long responseTime,
        String extraData,
        String signature
) {
}
