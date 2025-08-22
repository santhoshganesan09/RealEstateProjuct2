package com.example.RealEstateProjuct.controller;

import com.example.RealEstateProjuct.dto.PropertyDTO;
import com.example.RealEstateProjuct.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
public class PropertyController {


    private final PropertyService propertyService;

    // Create property
    @PostMapping
    public ResponseEntity<?> createProperty(@RequestBody PropertyDTO dto) {
        PropertyDTO saved = propertyService.createProperty(dto);
        return ResponseEntity.ok(
                new ApiResponse("success", "Property created successfully", saved)
        );
    }

    // Get property by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        PropertyDTO dto = propertyService.getPropertyById(id);
        return ResponseEntity.ok(
                new ApiResponse("success", "Property retrieved successfully", dto)
        );
    }

    // Get all properties
    @GetMapping
    public ResponseEntity<?> getAllProperties() {
        List<PropertyDTO> list = propertyService.getAllProperties();
        return ResponseEntity.ok(
                new ApiResponse("success", "Properties retrieved successfully", list)
        );
    }

    // Update property
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody PropertyDTO dto) {
        PropertyDTO updated = propertyService.updateProperty(id, dto);
        return ResponseEntity.ok(
                new ApiResponse("success", "Property updated successfully", updated)
        );
    }

    // Delete property
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.ok(
                new ApiResponse("success", "Property deleted successfully", null)
        );
    }

    @GetMapping("/filter")
    public List<PropertyDTO> filterProperties(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String amenity,
            @RequestParam(required = false) String createdByRole,
            @RequestParam(required = false) String subtype,
            @RequestParam( name = "isVerified", required = false) Boolean verified,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String city) {

        return propertyService.filterProperties(category, minPrice, maxPrice,
                amenity, createdByRole, subtype, verified, location, city);
    }



    // --- Inner static class for API response structure ---
    @lombok.Data
    @lombok.AllArgsConstructor
    static class ApiResponse {
        private String status;
        private String message;
        private Object data;
    }


}
