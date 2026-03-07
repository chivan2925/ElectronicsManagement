package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CreateReviewRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.ReviewResponseDTO;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ==================== Customer / Public ====================

    @GetMapping("/api/products/{productId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getProductReviews(@PathVariable Integer productId) {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getProductReviews(productId)));
    }

    @PostMapping("/api/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> createReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateReviewRequestDTO request) {
        ReviewResponseDTO response = reviewService.createReview(userDetails.getUsername(), request);
        return ResponseEntity.ok(ApiResponse.success("Đánh giá của bạn đã được ghi nhận và đang chờ duyệt", response));
    }

    // ==================== Admin ====================

    @GetMapping("/api/admin/reviews")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(reviewService.getAllReviews()));
    }

    @PutMapping("/api/admin/reviews/{id}/approve")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> approve(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Duyệt đánh giá thành công", reviewService.approveReview(id)));
    }

    @PutMapping("/api/admin/reviews/{id}/reject")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> reject(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success("Từ chối đánh giá thành công", reviewService.rejectReview(id)));
    }
}
