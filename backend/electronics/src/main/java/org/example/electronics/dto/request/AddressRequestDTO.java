package org.example.electronics.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressRequestDTO (
        @NotNull(message = "ID của user không được null")
        @Schema(description = "ID của user là người có địa chỉ này.", example = "1")
        Integer userId,

        @NotBlank(message = "Nhãn hiệu của địa chỉ không được bỏ trống")
        @Size(max = 50, message = "Nhãn hiệu của địa chỉ không được vượt quá 50 kí tự")
        String label,

        @NotBlank(message = "Địa chỉ chi tiết không được bỏ trống")
        @Size(max = 255, message = "Địa chỉ chi tiết không được vượt quá 255 kí tự")
        String line,

        @NotBlank(message = "Phường/Xã của địa chỉ không được bỏ trống")
        @Size(max = 50, message = "Phường/Xã của địa chỉ không được vượt quá 50 kí tự")
        String ward,

        @NotBlank(message = "Quận/Huyện của địa chỉ không được bỏ trống")
        @Size(max = 50, message = "Quận/Huyện của địa chỉ không được vượt quá 50 kí tự")
        String district,

        @NotBlank(message = "Tỉnh/Thành phố của địa chỉ không được bỏ trống")
        @Size(max = 50, message = "Tỉnh/Thành phố của địa chỉ không được vượt quá 50 kí tự")
        String province,

        String note,

        Boolean defaultAddress
) {
}
