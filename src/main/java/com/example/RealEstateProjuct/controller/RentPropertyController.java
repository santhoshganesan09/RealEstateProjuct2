package com.example.RealEstateProjuct.controller;

import com.example.RealEstateProjuct.dto.PropertyDTO;
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

    //  Create Rent Property
    @PostMapping
    public ResponseEntity<PropertyDTO> createRentProperty(@RequestBody PropertyDTO dto) {
        return ResponseEntity.ok(rentPropertyService.createRentProperty(dto));
    }

    //  Get Rent Property by ID
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getRentPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(rentPropertyService.getRentPropertyById(id));
    }

    //  Get All Rent Properties
    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllRentProperties() {
        return ResponseEntity.ok(rentPropertyService.getAllRentProperties());
    }

    //  Update Rent Property
    @PutMapping("/{id}")
    public ResponseEntity<PropertyDTO> updateRentProperty(
            @PathVariable Long id,
            @RequestBody PropertyDTO dto
    ) {
        return ResponseEntity.ok(rentPropertyService.updateRentProperty(id, dto));
    }

    //  Delete Rent Property
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentProperty(@PathVariable Long id) {
        rentPropertyService.deleteRentProperty(id);
        return ResponseEntity.noContent().build();
    }

    //  Filter Rent Properties
    @GetMapping("/filter")
    public ResponseEntity<List<PropertyDTO>> filterRentProperties(
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
        return ResponseEntity.ok(
                rentPropertyService.filterRentProperties(category, minPrice, maxPrice,
                        amenity, createdByRole, subtype, verified, location, city)
        );
    }


}
