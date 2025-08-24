package com.example.RealEstateProjuct.repo.rentRepo;

import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentFlatDetailsRepo extends JpaRepository<RentFlatDetails, Long> {


    @Query("SELECT f FROM RentFlatDetails f " +
            "LEFT JOIN FETCH f.amenities a " +
            "WHERE f.id = :flatId")
    Optional<RentFlatDetails> findByIdWithAmenities(@Param("flatId") Long flatId);
}
