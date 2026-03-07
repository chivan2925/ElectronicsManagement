package org.example.electronics.service;

import org.example.electronics.dto.request.CategoryRequestDTO;
import org.example.electronics.dto.response.CategoryResponseDTO;
import org.example.electronics.entity.CategoryEntity;
import org.example.electronics.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ========== Public ==========

    public List<CategoryResponseDTO> getTree() {
        List<CategoryEntity> roots = categoryRepository.findByParentIsNull();
        return roots.stream().map(this::toTreeDTO).toList();
    }

    // ========== Admin ==========

    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll().stream().map(this::toFlatDTO).toList();
    }

    public CategoryResponseDTO getById(Integer id) {
        return toFlatDTO(findById(id));
    }

    public CategoryResponseDTO create(CategoryRequestDTO request) {
        CategoryEntity category = CategoryEntity.builder()
                .name(request.name())
                .slug(request.slug())
                .iconUrl(request.iconUrl())
                .build();

        if (request.parentId() != null) {
            CategoryEntity parent = findById(request.parentId());
            category.setParent(parent);
        }

        categoryRepository.save(category);
        return toFlatDTO(category);
    }

    public CategoryResponseDTO update(Integer id, CategoryRequestDTO request) {
        CategoryEntity category = findById(id);
        category.setName(request.name());
        category.setSlug(request.slug());
        category.setIconUrl(request.iconUrl());

        if (request.parentId() != null) {
            CategoryEntity parent = findById(request.parentId());
            category.setParent(parent);
        } else {
            category.setParent(null);
        }

        categoryRepository.save(category);
        return toFlatDTO(category);
    }

    public void delete(Integer id) {
        CategoryEntity category = findById(id);
        categoryRepository.delete(category);
    }

    // ========== Helpers ==========

    private CategoryEntity findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục"));
    }

    private CategoryResponseDTO toFlatDTO(CategoryEntity c) {
        return new CategoryResponseDTO(
                c.getId(), c.getName(), c.getIconUrl(), c.getSlug(),
                c.getParent() != null ? c.getParent().getId() : null,
                c.getStatus(), c.getCreatedAt(), c.getUpdatedAt()
        );
    }

    private CategoryResponseDTO toTreeDTO(CategoryEntity c) {
        // The existing CategoryResponseDTO doesn't have children field,
        // so we use the flat DTO approach. The tree structure is implicitly
        // available through the parentId field.
        return toFlatDTO(c);
    }
}
