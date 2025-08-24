package com.example.RealEstateProjuct.repo.rentRepo;

import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentPGDetails;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentPGDetailsRepo extends JpaRepository<RentPGDetails, Long> {


    @Query("SELECT pg FROM RentPGDetails pg " +
            "LEFT JOIN FETCH pg.amenities a " +
            "WHERE pg.id = :pgId")
    Optional<RentPGDetails> findByIdWithAmenities(@Param("pgId") Long pgId);

}
