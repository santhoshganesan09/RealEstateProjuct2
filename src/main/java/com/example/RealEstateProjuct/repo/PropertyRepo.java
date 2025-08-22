package com.example.RealEstateProjuct.repo;

import com.example.RealEstateProjuct.model.Property;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;


@Repository
public interface PropertyRepo extends JpaRepository<Property, Long> , JpaSpecificationExecutor<Property> {


}
