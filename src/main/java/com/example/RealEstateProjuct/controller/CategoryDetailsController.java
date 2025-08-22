package com.example.RealEstateProjuct.controller;

import com.example.RealEstateProjuct.model.CategoryDetails;
import com.example.RealEstateProjuct.service.CategoryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/category_details")
public class CategoryDetailsController {

    @Autowired
    private CategoryDetailsService categoryDetailsService;

    public CategoryDetailsController(CategoryDetailsService categoryDetailsService) {
        this.categoryDetailsService = categoryDetailsService;
    }

    // GET details by category
    @GetMapping("/{categoryId}")
    public List<CategoryDetails> getCategoryDetails(@PathVariable Long categoryId) {
        return categoryDetailsService.getDetailsByCategory(categoryId);


    }
}
