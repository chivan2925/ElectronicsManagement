package org.example.electronics.service.admin;

import org.example.electronics.dto.request.CategoryRequestDTO;
import org.example.electronics.dto.response.CategoryResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCategoryService {

    //Admin
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO categoryRequestDTO);
    void deleteCategory(Integer categoryId);
    Page<CategoryResponseDTO> getAllParentCategoriesPage(Pageable pageable);
    Page<CategoryResponseDTO> getAllSubCategoriesPage(Integer parentId, Pageable pageable);
    CategoryResponseDTO getCategoryById(Integer categoryId);
}
