package com.example.RealEstateProjuct.repo;

import com.example.RealEstateProjuct.model.PropertyDetails.HouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseDetailsRepo extends JpaRepository<HouseDetails, Long> {
}
