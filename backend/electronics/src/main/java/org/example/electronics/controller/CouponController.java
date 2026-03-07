package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CouponRequestDTO;
import org.example.electronics.dto.request.CouponValidateRequestDTO;
import org.example.electronics.dto.response.*;
import org.example.electronics.entity.CartEntity;
import org.example.electronics.entity.CartDetailEntity;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.repository.CartDetailRepository;
import org.example.electronics.repository.CartRepository;
import org.example.electronics.repository.UserRepository;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class CouponController {

    private final CouponService couponService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;

    public CouponController(CouponService couponService,
                            UserRepository userRepository,
                            CartRepository cartRepository,
                            CartDetailRepository cartDetailRepository) {
        this.couponService = couponService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
    }

    // ==================== Customer ====================

    @PostMapping("/api/coupons/validate")
    public ResponseEntity<ApiResponse<CouponValidateResponseDTO>> validate(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CouponValidateRequestDTO request) {

        // Calculate cart total for the current user
        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        BigDecimal cartTotal = BigDecimal.ZERO;
        CartEntity cart = cartRepository.findByUserId(user.getId()).orElse(null);
        if (cart != null) {
            List<CartDetailEntity> items = cartDetailRepository.findByCartId(cart.getId());
            cartTotal = items.stream()
                    .map(i -> i.getVariant().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        CouponValidateResponseDTO response = couponService.validate(request.code(), cartTotal);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ==================== Admin ====================

    @GetMapping("/api/admin/coupons")
    @RequirePermission("MANAGE_COUPON")
    public ResponseEntity<ApiResponse<List<CouponResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(couponService.getAll()));
    }

    @GetMapping("/api/admin/coupons/{id}")
    @RequirePermission("MANAGE_COUPON")
    public ResponseEntity<ApiResponse<CouponResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(couponService.getById(id)));
    }

    @PostMapping("/api/admin/coupons")
    @RequirePermission("MANAGE_COUPON")
    public ResponseEntity<ApiResponse<CouponResponseDTO>> create(
            @Valid @RequestBody CouponRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo coupon thành công", couponService.create(request)));
    }

    @PutMapping("/api/admin/coupons/{id}")
    @RequirePermission("MANAGE_COUPON")
    public ResponseEntity<ApiResponse<CouponResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CouponRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật coupon thành công", couponService.update(id, request)));
    }

    @DeleteMapping("/api/admin/coupons/{id}")
    @RequirePermission("MANAGE_COUPON")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        couponService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa coupon thành công", null));
    }
}
