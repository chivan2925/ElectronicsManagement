package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.ChangePasswordRequestDTO;
import org.example.electronics.dto.request.UpdateUserRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.UserResponseDTO;
import org.example.electronics.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserResponseDTO response = userService.getProfile(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateUserRequestDTO request) {
        UserResponseDTO response = userService.updateProfile(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", response));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ChangePasswordRequestDTO request) {
        userService.changePassword(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Đổi mật khẩu thành công", null));
    }
}
