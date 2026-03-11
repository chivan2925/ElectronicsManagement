package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.admin.AdminCategoryRequestDTO;
import org.example.electronics.dto.request.admin.AdminUpdateProductStatusRequestDTO;
import org.example.electronics.dto.response.admin.AdminCategoryResponseDTO;
import org.example.electronics.entity.CategoryEntity;
import org.example.electronics.entity.enums.ProductStatus;
import org.example.electronics.mapper.CategoryMapper;
import org.example.electronics.repository.CategoryRepository;
import org.example.electronics.service.admin.AdminCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public AdminCategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    @Override
    public AdminCategoryResponseDTO createCategory(AdminCategoryRequestDTO adminCategoryRequestDTO) {
        if(categoryRepository.existsBySlug(adminCategoryRequestDTO.slug())) {
            throw new IllegalArgumentException("Slug này đã tồn tại. Vui lòng chọn slug khác");
        }

        if(adminCategoryRequestDTO.parentId() != null) {
            if(!categoryRepository.existsById(adminCategoryRequestDTO.parentId())) {
                throw new IllegalArgumentException("Không tồn tại danh mục cha với ID này");
            }
        }

        CategoryEntity newCategoryEntity = categoryMapper.toEntity(adminCategoryRequestDTO);

        newCategoryEntity = categoryRepository.save(newCategoryEntity);

        return categoryMapper.toResponseDTO(newCategoryEntity);
    }

    @Transactional
    @Override
    public AdminCategoryResponseDTO updateCategory(Integer categoryId, AdminCategoryRequestDTO adminCategoryRequestDTO) {
        if(categoryRepository.existsBySlugAndIdNot(adminCategoryRequestDTO.slug(), categoryId)) {
            throw new IllegalArgumentException("Slug này đã bị danh mục khác sử dụng. Vui lòng chọn slug khác");
        }

        if(adminCategoryRequestDTO.parentId() != null) {
            if(adminCategoryRequestDTO.parentId().equals(categoryId)) {
                throw new IllegalArgumentException("Không thể chọn chính danh mục này làm danh mục cha");
            }
        }

        CategoryEntity existingCategoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + categoryId
                ));

        categoryMapper.updateEntityFromDTO(adminCategoryRequestDTO, existingCategoryEntity);

        if(adminCategoryRequestDTO.parentId() != null) {
            CategoryEntity newParentCategoryEntity = categoryRepository.findById(adminCategoryRequestDTO.parentId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Không tìm thấy danh mục cha với id: " + adminCategoryRequestDTO.parentId()
                    ));
            existingCategoryEntity.setParent(newParentCategoryEntity);
        }
        else {
            existingCategoryEntity.setParent(null);
        }

        existingCategoryEntity = categoryRepository.save(existingCategoryEntity);

        return categoryMapper.toResponseDTO(existingCategoryEntity);
    }

    @Transactional
    @Override
    public AdminCategoryResponseDTO updateStatusCategory(Integer categoryId, AdminUpdateProductStatusRequestDTO adminUpdateProductStatusRequestDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + categoryId
                ));

        categoryEntity.setStatus(adminUpdateProductStatusRequestDTO.status());

        categoryEntity = categoryRepository.save(categoryEntity);

        return categoryMapper.toResponseDTO(categoryEntity);
    }

    @Transactional
    @Override
    public void deleteCategory(Integer categoryId) {
        CategoryEntity existingCategoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + categoryId
                ));

        if(categoryRepository.existsByParentId(existingCategoryEntity.getId())) {
            throw new IllegalStateException("Không thể xóa! Danh mục này đang chứa các danh mục con bên trong. Vui lòng xóa hoặc di chuyển danh mục con trước.");
        }

        /*
        if (productRepository.existsByCategoryId(categoryId)) {
             throw new IllegalStateException("Không thể xóa! Đang có sản phẩm thuộc danh mục này.");
        }
        */

        existingCategoryEntity.setStatus(ProductStatus.DELETED);

        categoryRepository.save(existingCategoryEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminCategoryResponseDTO> getAllParentCategories(Pageable pageable) {
        Page<CategoryEntity> categoryEntityPage = categoryRepository.findByParentIdIsNull(pageable);

        return categoryEntityPage.map(categoryMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminCategoryResponseDTO> getAllSubCategories(Integer parentId, Pageable pageable) {
        if(!categoryRepository.existsById(parentId)) {
            throw new IllegalArgumentException("Không tìm thấy danh mục cha với ID: " + parentId);
        }

        Page<CategoryEntity> categoryEntityPage = categoryRepository.findByParentId(parentId, pageable);

        return categoryEntityPage.map(categoryMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminCategoryResponseDTO getCategoryById(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + categoryId
                ));

        return categoryMapper.toResponseDTO(categoryEntity);
    }
}
