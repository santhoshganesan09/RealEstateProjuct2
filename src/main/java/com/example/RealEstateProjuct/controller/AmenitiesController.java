package com.example.RealEstateProjuct.controller;


import com.example.RealEstateProjuct.model.Amenity;
import com.example.RealEstateProjuct.service.AmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/amenities")
public class AmenitiesController {

    @Autowired
    private AmenityService amenityService;

    @PostMapping
    public ResponseEntity<Amenity> createAmenity(@RequestParam String name) {
        Amenity amenity = amenityService.createAmenity(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(amenity);
    }

    @GetMapping
    public ResponseEntity<List<Amenity>> getAllAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        return ResponseEntity.ok(amenities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
        return ResponseEntity.noContent().build();
    }
}
