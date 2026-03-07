package org.example.electronics.controller;

import jakarta.validation.Valid;
import org.example.electronics.dto.request.CategoryRequestDTO;
import org.example.electronics.dto.response.ApiResponse;
import org.example.electronics.dto.response.CategoryResponseDTO;
import org.example.electronics.dto.response.CategoryTreeResponseDTO;
import org.example.electronics.entity.CategoryEntity;
import org.example.electronics.repository.CategoryRepository;
import org.example.electronics.security.RequirePermission;
import org.example.electronics.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    // ==================== Public ====================

    @GetMapping("/api/categories")
    public ResponseEntity<ApiResponse<List<CategoryTreeResponseDTO>>> getTree() {
        List<CategoryEntity> roots = categoryRepository.findByParentIsNull();
        List<CategoryTreeResponseDTO> tree = roots.stream().map(this::toTree).toList();
        return ResponseEntity.ok(ApiResponse.success(tree));
    }

    // ==================== Admin ====================

    @GetMapping("/api/admin/categories")
    @RequirePermission("MANAGE_CATEGORY")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getAll()));
    }

    @GetMapping("/api/admin/categories/{id}")
    @RequirePermission("MANAGE_CATEGORY")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(ApiResponse.success(categoryService.getById(id)));
    }

    @PostMapping("/api/admin/categories")
    @RequirePermission("MANAGE_CATEGORY")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> create(
            @Valid @RequestBody CategoryRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo danh mục thành công", categoryService.create(request)));
    }

    @PutMapping("/api/admin/categories/{id}")
    @RequirePermission("MANAGE_CATEGORY")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> update(
            @PathVariable Integer id,
            @Valid @RequestBody CategoryRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật danh mục thành công", categoryService.update(id, request)));
    }

    @DeleteMapping("/api/admin/categories/{id}")
    @RequirePermission("MANAGE_CATEGORY")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa danh mục thành công", null));
    }

    // ==================== Helpers ====================

    private CategoryTreeResponseDTO toTree(CategoryEntity c) {
        List<CategoryTreeResponseDTO> children = c.getSubCategoryList() != null
                ? c.getSubCategoryList().stream().map(this::toTree).toList()
                : List.of();
        return new CategoryTreeResponseDTO(
                c.getId(), c.getName(), c.getIconUrl(), c.getSlug(),
                c.getStatus(), children
        );
    }
}
