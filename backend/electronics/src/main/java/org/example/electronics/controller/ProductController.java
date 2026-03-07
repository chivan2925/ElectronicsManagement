package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.ProductRequestDTO;
import org.example.electronics.dto.request.VariantRequestDTO;
import org.example.electronics.dto.response.*;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.ProductService;
import org.example.electronics.service.VariantService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final VariantService variantService;

    public ProductController(ProductService productService, VariantService variantService) {
        this.productService = productService;
        this.variantService = variantService;
    }

    // ==================== Public ====================

    @GetMapping("/api/products")
    public ResponseEntity<ApiResponse<Page<ProductResponseDTO>>> search(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<ProductResponseDTO> result = productService.search(
                categoryId, brandId, keyword, minPrice, maxPrice,
                sortBy, sortDir, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/api/products/{slug}")
    public ResponseEntity<ApiResponse<ProductDetailResponseDTO>> getBySlug(@PathVariable String slug) {
        ProductDetailResponseDTO response = productService.getBySlug(slug);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/api/variants/{id}")
    public ResponseEntity<ApiResponse<VariantResponseDTO>> getVariant(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(variantService.getById(id)));
    }

    // ==================== Admin ====================

    @PostMapping("/api/admin/products")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> create(
            @Valid @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo sản phẩm thành công", productService.create(request)));
    }

    @PutMapping("/api/admin/products/{id}")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật sản phẩm thành công", productService.update(id, request)));
    }

    @DeleteMapping("/api/admin/products/{id}")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        productService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa sản phẩm thành công", null));
    }

    // ---------- Variant (Admin) ----------

    @PostMapping("/api/admin/products/{productId}/variants")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<VariantResponseDTO>> createVariant(
            @PathVariable Integer productId,
            @Valid @RequestBody VariantRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo biến thể thành công",
                variantService.create(productId, request)));
    }

    @PutMapping("/api/admin/variants/{id}")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<VariantResponseDTO>> updateVariant(
            @PathVariable Integer id,
            @Valid @RequestBody VariantRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật biến thể thành công",
                variantService.update(id, request)));
    }

    @DeleteMapping("/api/admin/variants/{id}")
    @RequirePermission("MANAGE_PRODUCT")
    public ResponseEntity<ApiResponse<Void>> deleteVariant(@PathVariable Integer id) {
        variantService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa biến thể thành công", null));
    }
}
