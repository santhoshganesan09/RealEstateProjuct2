package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.dto.PropertyDTO;
import com.example.RealEstateProjuct.model.RentProperty.RentProperty;

import java.util.List;

public interface RentPropertyService {

    PropertyDTO createRentProperty(PropertyDTO propertyDTO);

    PropertyDTO getRentPropertyById(Long id);

    List<PropertyDTO> getAllRentProperties();

    PropertyDTO updateRentProperty(Long id, PropertyDTO propertyDTO);

    void deleteRentProperty(Long id);

    List<PropertyDTO> filterRentProperties(String category,
                                           Double minPrice,
                                           Double maxPrice,
                                           String amenity,
                                           String createdByRole,
                                           String subtype,
                                           Boolean verified,
                                           String location,
                                           String city);

}
