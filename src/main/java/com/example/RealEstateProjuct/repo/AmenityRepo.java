package com.example.RealEstateProjuct.repo;

import com.example.RealEstateProjuct.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AmenityRepo extends JpaRepository<Amenity, Long> {

    Optional<Amenity> findByName(String name);


}
