package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import org.example.electronics.dto.request.CategoryRequestDTO;
import org.example.electronics.dto.response.CategoryResponseDTO;
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

    //Admin
    @Transactional
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if(categoryRequestDTO == null){
            throw new IllegalArgumentException("Tham số truyền vào categoryRequestDTO không được null");
        }

        if(categoryRepository.existsBySlug(categoryRequestDTO.slug())) {
            throw new IllegalArgumentException("Slug này đã tồn tại. Vui lòng chọn slug khác");
        }

        if(categoryRequestDTO.parentId() != null) {
            if(!categoryRepository.existsById(categoryRequestDTO.parentId())) {
                throw new IllegalArgumentException("Không tồn tại danh mục cha với ID này");
            }
        }

        CategoryEntity newCategoryEntity = categoryMapper.toEntity(categoryRequestDTO);

        newCategoryEntity = categoryRepository.save(newCategoryEntity);

        return categoryMapper.toResponseDTO(newCategoryEntity);
    }

    @Transactional
    @Override
    public CategoryResponseDTO updateCategory(Integer categoryId, CategoryRequestDTO categoryRequestDTO) {
        if(categoryId == null || categoryRequestDTO == null) {
            throw new IllegalArgumentException("Tham số truyền vào categoryId, categoryRequestDTO không được null");
        }

        if(categoryRepository.existsBySlugAndIdNot(categoryRequestDTO.slug(), categoryId)) {
            throw new IllegalArgumentException("Slug này đã bị danh mục khác sử dụng. Vui lòng chọn slug khác");
        }

        if(categoryRequestDTO.parentId() != null) {
            if(categoryRequestDTO.parentId().equals(categoryId)) {
                throw new IllegalArgumentException("Không thể chọn chính danh mục này làm danh mục cha");
            }
            if(!categoryRepository.existsById(categoryRequestDTO.parentId())) {
                throw new IllegalArgumentException("Không tồn tại danh mục cha với ID này");
            }
        }

        CategoryEntity existingCategoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + categoryId
                ));

        categoryMapper.updateEntityFromDTO(categoryRequestDTO, existingCategoryEntity);

        existingCategoryEntity = categoryRepository.save(existingCategoryEntity);

        return categoryMapper.toResponseDTO(existingCategoryEntity);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        if(categoryId == null) {
            throw new IllegalArgumentException("Tham số truyền vào categoryId không được null");
        }

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

    @Override
    public Page<CategoryResponseDTO> getAllParentCategories(Pageable pageable) {
        Page<CategoryEntity> categoryEntityPage = categoryRepository.findByParentIdIsNull(pageable);

        return categoryEntityPage.map(categoryMapper::toResponseDTO);
    }

    @Override
    public Page<CategoryResponseDTO> getAllSubCategories(Integer parentId, Pageable pageable) {
        if(parentId == null) {
            throw new IllegalArgumentException("Tham số truyền vào parentId không được null");
        }

        if(!categoryRepository.existsById(parentId)) {
            throw new IllegalArgumentException("Không tìm thấy danh mục cha với ID: " + parentId);
        }

        Page<CategoryEntity> categoryEntityPage = categoryRepository.findByParentId(parentId, pageable);

        return categoryEntityPage.map(categoryMapper::toResponseDTO);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Integer categoryId) {
        if(categoryId == null) {
            throw new IllegalArgumentException("Tham số truyền vào categoryId không được null");
        }

        CategoryEntity categoryEntity = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy danh mục với id: " + categoryId
                ));

        return categoryMapper.toResponseDTO(categoryEntity);
    }
}
