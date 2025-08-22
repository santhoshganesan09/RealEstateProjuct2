package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.model.Amenity;

import java.util.List;

public interface AmenityService {

    Amenity createAmenity(String name);
    List<Amenity> getAllAmenities();
    Amenity getAmenityById(Long id);
    void deleteAmenity(Long id);
}
