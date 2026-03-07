package org.example.electronics.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(
        @NotBlank(message = "Tên người nhận không được để trống")
        String receiverName,

        @NotBlank(message = "Số điện thoại không được để trống")
        String phone,

        @NotBlank(message = "Địa chỉ không được để trống")
        String line,

        @NotBlank(message = "Phường/Xã không được để trống")
        String ward,

        @NotBlank(message = "Quận/Huyện không được để trống")
        String district,

        @NotBlank(message = "Tỉnh/Thành phố không được để trống")
        String province,

        Boolean isDefault
) {}
