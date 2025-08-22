package com.example.RealEstateProjuct.repo.rentRepo;

import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentFlatDetailsRepo extends JpaRepository<RentFlatDetails, Long> {
}
