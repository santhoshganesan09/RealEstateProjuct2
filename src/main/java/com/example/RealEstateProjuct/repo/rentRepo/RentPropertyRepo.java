package com.example.RealEstateProjuct.repo.rentRepo;

import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentPropertyRepo extends JpaRepository<RentProperty,Long>, JpaSpecificationExecutor<RentProperty> {

    // Fetch everything in one go (property + pg/flat/commercial + amenities)
    @EntityGraph(attributePaths = {
            "amenities",
            "pgDetails", "pgDetails.amenities",
            "flatDetails", "flatDetails.amenities",
            "commercialDetails", "commercialDetails.amenities"
    })
    Optional<RentProperty> findWithAllDetailsById(Long id);

}
