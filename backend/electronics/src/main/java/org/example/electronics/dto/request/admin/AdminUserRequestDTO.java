package org.example.electronics.dto.request.admin;

import jakarta.validation.constraints.*;
import org.example.electronics.entity.enums.GenderType;
import org.example.electronics.entity.enums.UserStatus;

import java.time.LocalDate;

public record AdminUserRequestDTO(
        @NotBlank(message = "Họ tên đầy đủ của người dùng không được bỏ trống")
        @Size(max = 100, message = "Họ tên đầy đủ của người dùng không được vượt quá 25 kí tự")
        String fullName,

        @NotNull(message = "Giới tính của người dùng không được bỏ trống")
        GenderType gender,

        @Past(message = "Ngày sinh của người dùng phải là một ngày trong quá khứ")
        LocalDate dateOfBirth,

        @NotBlank(message = "Username của người dùng không được bỏ trống")
        @Size(max = 25, message = "Username của người dùng không được vượt quá 25 kí tự")
        String username,

        String avatarUrl,

        @NotBlank(message = "Email của người dùng không được bỏ trống")
        @Email(message = "Email của người dùng không đúng định dạng")
        String email,

        @NotBlank(message = "Số điện thoại của người dùng không được bỏ trống")
        @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại của người dùng phải chứa 0-9 và có 10 chữ số")
        String phoneNumber,

        @NotBlank(message = "Mật khẩu của người dùng không được bỏ trống")
        @Size(min = 8, max = 20, message = "Mật khẩu của người dùng phải có độ dài từ 8 - 20 kí tự")
        String password,

        @NotNull(message = "Trạng thái của người dùng không được bỏ trống")
        UserStatus status
) {

}
