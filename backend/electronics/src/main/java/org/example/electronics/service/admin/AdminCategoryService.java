package org.example.electronics.service.admin;

import org.example.electronics.dto.request.admin.AdminUpdateProductStatusRequestDTO;
import org.example.electronics.dto.request.admin.AdminCategoryRequestDTO;
import org.example.electronics.dto.response.admin.AdminCategoryResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCategoryService {

    //Admin
    AdminCategoryResponseDTO createCategory(AdminCategoryRequestDTO adminCategoryRequestDTO);
    AdminCategoryResponseDTO updateCategory(Integer categoryId, AdminCategoryRequestDTO adminCategoryRequestDTO);
    AdminCategoryResponseDTO updateStatusCategory(Integer categoryId, AdminUpdateProductStatusRequestDTO adminUpdateProductStatusRequestDTO);
    void deleteCategory(Integer categoryId);
    Page<AdminCategoryResponseDTO> getAllParentCategories(Pageable pageable);
    Page<AdminCategoryResponseDTO> getAllSubCategories(Integer parentId, Pageable pageable);
    AdminCategoryResponseDTO getCategoryById(Integer categoryId);
}
