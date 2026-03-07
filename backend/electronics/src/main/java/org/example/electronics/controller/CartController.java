package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CartItemRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.CartResponseDTO;
import org.example.electronics.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCart(
            @AuthenticationPrincipal UserDetails userDetails) {
        CartResponseDTO response = cartService.getCart(userDetails.getUsername());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartResponseDTO>> addOrUpdateItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CartItemRequestDTO request) {
        CartResponseDTO response = cartService.addOrUpdateItem(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Cập nhật giỏ hàng thành công", response));
    }

    @DeleteMapping("/items/{variantId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> removeItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer variantId) {
        CartResponseDTO response = cartService.removeItem(userDetails.getUsername(), variantId);
        return ResponseEntity.ok(ApiResponse.success("Xóa sản phẩm khỏi giỏ hàng thành công", response));
    }
}
