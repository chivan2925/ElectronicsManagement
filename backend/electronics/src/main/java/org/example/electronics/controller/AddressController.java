package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.AddressRequestDTO;
import org.example.electronics.dto.response.AddressResponseDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDTO>>> getAddresses(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<AddressResponseDTO> addresses = addressService.getAddresses(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(addresses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponseDTO>> createAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AddressRequestDTO request) {
        AddressResponseDTO response = addressService.createAddress(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Thêm địa chỉ thành công", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponseDTO>> updateAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer id,
            @Valid @RequestBody AddressRequestDTO request) {
        AddressResponseDTO response = addressService.updateAddress(userDetails.getUsername(), id, request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật địa chỉ thành công", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer id) {
        addressService.deleteAddress(userDetails.getUsername(), id);
        return ResponseEntity.ok(ApiResponse.success("Xóa địa chỉ thành công", null));
    }

    @PutMapping("/{id}/default")
    public ResponseEntity<ApiResponse<AddressResponseDTO>> setDefault(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer id) {
        AddressResponseDTO response = addressService.setDefault(userDetails.getUsername(), id);
        return ResponseEntity.ok(ApiResponse.success("Đặt địa chỉ mặc định thành công", response));
    }
}
