package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.model.CategoryDetails;

import java.util.List;

public interface CategoryDetailsService {

    List<CategoryDetails> getDetailsByCategory(Long categoryId);
}
