package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> createMultipleCategories(List<CategoryDTO> categoryDTOList);


}
