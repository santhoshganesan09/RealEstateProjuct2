package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.dto.RentDTO.RentPropertyDTO;


import java.util.List;

public interface RentPropertyService {

    // Create a new RentProperty
    RentPropertyDTO createRentProperty(RentPropertyDTO rentPropertyDTO);

    // Get a RentProperty by ID
    RentPropertyDTO getRentPropertyById(Long id);

    // Get all RentProperties
    List<RentPropertyDTO> getAllRentProperties();

    // Update an existing RentProperty
    RentPropertyDTO updateRentProperty(Long id, RentPropertyDTO rentPropertyDTO);

    // Delete a RentProperty by ID
    void deleteRentProperty(Long id);

    // Filter RentProperties by multiple criteria
    List<RentPropertyDTO> filterRentProperties(String category,
                                               Double minPrice,
                                               Double maxPrice,
                                               String amenity,
                                               String createdByRole,
                                               String subtype,
                                               Boolean verified,
                                               String location,
                                               String city);

}
