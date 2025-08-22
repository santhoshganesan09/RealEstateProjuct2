package com.example.RealEstateProjuct.service.serviceImpl;

import com.example.RealEstateProjuct.dto.CategoryDTO;
import com.example.RealEstateProjuct.exception.ResourceNotFoundException;
import com.example.RealEstateProjuct.mapper.CategoryMapper;
import com.example.RealEstateProjuct.model.Category;
import com.example.RealEstateProjuct.repo.CategoryRepo;
import com.example.RealEstateProjuct.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;



import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private  CategoryRepo categoryRepo;
    @Autowired
    private  CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        log.info("Creating new category: {}", categoryDto.getName());
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepo.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        log.info("Fetching category by id: {}", id);
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        log.info("Fetching all categories");
        return categoryRepo.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> createMultipleCategories(List<CategoryDTO> categoryDTOList) {
        List<Category> categories = categoryDTOList.stream()
                .map(categoryMapper::toEntity)
                .toList();
        List<Category> savedCategories = categoryRepo.saveAll(categories);
        return savedCategories.stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDto) {
        log.info("Updating category with id: {}", id);
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());

        Category updatedCategory = categoryRepo.save(existingCategory);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Deleting category with id: {}", id);
        if (!categoryRepo.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepo.deleteById(id);
    }

}
