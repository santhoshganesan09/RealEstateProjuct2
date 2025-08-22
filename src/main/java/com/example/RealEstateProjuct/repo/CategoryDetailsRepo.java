package com.example.RealEstateProjuct.repo;

import com.example.RealEstateProjuct.model.CategoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDetailsRepo extends JpaRepository<CategoryDetails, Long> {
    List<CategoryDetails> findByCategoryId(Long categoryId);
}
