package com.example.RealEstateProjuct.repo.rentRepo;

import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentPropertyRepo extends JpaRepository<RentProperty,Long>, JpaSpecificationExecutor<RentProperty> {


}
