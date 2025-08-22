package com.example.RealEstateProjuct.service.serviceImpl;


import com.example.RealEstateProjuct.model.CategoryDetails;
import com.example.RealEstateProjuct.repo.CategoryDetailsRepo;
import com.example.RealEstateProjuct.service.CategoryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDetailsServiceImpl implements CategoryDetailsService {

    @Autowired
    private CategoryDetailsRepo categoryDetailsRepo;


    @Override
    public List<CategoryDetails> getDetailsByCategory(Long categoryId) {
        return categoryDetailsRepo.findByCategoryId(categoryId);
    }
}
