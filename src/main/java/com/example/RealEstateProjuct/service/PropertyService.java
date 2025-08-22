package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.dto.PropertyDTO;

import java.util.List;


public interface PropertyService {

    PropertyDTO createProperty(PropertyDTO propertyDTO);

    PropertyDTO getPropertyById(Long id);

    List<PropertyDTO> getAllProperties();

    PropertyDTO updateProperty(Long id, PropertyDTO propertyDTO);

    void deleteProperty(Long id);


    List<PropertyDTO> filterProperties(String category,
                                       Double minPrice,
                                       Double maxPrice,
                                       String amenity,
                                       String createdByRole,
                                       String subtype,
                                       Boolean verified,
                                       String location,
                                       String city);


}
