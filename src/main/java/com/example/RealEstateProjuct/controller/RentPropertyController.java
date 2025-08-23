package com.example.RealEstateProjuct.controller;

import com.example.RealEstateProjuct.dto.PropertyDTO;
import com.example.RealEstateProjuct.dto.RentDTO.RentPropertyDTO;
import com.example.RealEstateProjuct.service.RentPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rent-property")
@RequiredArgsConstructor
public class RentPropertyController {

    private final RentPropertyService rentPropertyService;

    // Create a new RentProperty
    @PostMapping
    public ResponseEntity<RentPropertyDTO> createRentProperty(@RequestBody RentPropertyDTO dto) {
        RentPropertyDTO created = rentPropertyService.createRentProperty(dto);
        return ResponseEntity.ok(created);
    }

    // Get a RentProperty by ID
    @GetMapping("/{id}")
    public ResponseEntity<RentPropertyDTO> getRentPropertyById(@PathVariable Long id) {
        RentPropertyDTO property = rentPropertyService.getRentPropertyById(id);
        return ResponseEntity.ok(property);
    }

    // Get all RentProperties
    @GetMapping
    public ResponseEntity<List<RentPropertyDTO>> getAllRentProperties() {
        List<RentPropertyDTO> properties = rentPropertyService.getAllRentProperties();
        return ResponseEntity.ok(properties);
    }

    // Update a RentProperty by ID
    @PutMapping("/{id}")
    public ResponseEntity<RentPropertyDTO> updateRentProperty(@PathVariable Long id,
                                                              @RequestBody RentPropertyDTO dto) {
        RentPropertyDTO updated = rentPropertyService.updateRentProperty(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete a RentProperty by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentProperty(@PathVariable Long id) {
        rentPropertyService.deleteRentProperty(id);
        return ResponseEntity.noContent().build();
    }

    // Filter RentProperties
    @GetMapping("/filter")
    public ResponseEntity<List<RentPropertyDTO>> filterRentProperties(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String amenity,
            @RequestParam(required = false) String createdByRole,
            @RequestParam(required = false) String subtype,
            @RequestParam(required = false) Boolean verified,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String city
    ) {
        List<RentPropertyDTO> filtered = rentPropertyService.filterRentProperties(
                category, minPrice, maxPrice, amenity, createdByRole, subtype, verified, location, city
        );
        return ResponseEntity.ok(filtered);
    }



}
