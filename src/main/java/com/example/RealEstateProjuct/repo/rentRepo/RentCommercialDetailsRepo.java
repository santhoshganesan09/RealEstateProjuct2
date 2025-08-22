package com.example.RealEstateProjuct.repo.rentRepo;

import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentCommercialDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentCommercialDetailsRepo extends JpaRepository<RentCommercialDetails, Long> {
}
