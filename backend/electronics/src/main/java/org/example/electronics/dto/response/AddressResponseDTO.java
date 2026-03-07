package org.example.electronics.dto.response;

import java.time.LocalDateTime;

public record AddressResponseDTO(
        Integer id,
        String receiverName,
        String phone,
        String line,
        String ward,
        String district,
        String province,
        Boolean isDefault,
        LocalDateTime createdAt
) {}
