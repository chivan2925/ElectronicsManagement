package org.example.electronics.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        Integer id,
        List<CartItemResponseDTO> items,
        BigDecimal totalAmount,
        Integer totalItems
) {}
