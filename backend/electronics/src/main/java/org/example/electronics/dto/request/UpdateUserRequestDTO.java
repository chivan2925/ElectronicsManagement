package org.example.electronics.dto.request;

public record UpdateUserRequestDTO(
        String name,
        String avatar,
        String phoneNumber
) {}
