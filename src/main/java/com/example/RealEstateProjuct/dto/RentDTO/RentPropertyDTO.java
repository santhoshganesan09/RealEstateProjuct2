package com.example.RealEstateProjuct.dto.RentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentPropertyDTO {

    private Long id;
    private String title;
    private String propertyType; // FLAT / PG / COMMERCIAL

    // Category info
    private Long categoryId;
    private String categoryName;

    // Owner info (flat)
    private Long ownerId;
    private String ownerName;
    private String ownerCompanyName;
    private String ownerPhone;

    // Rent info
    private Double amount;
    private String currency;
    private String type; // MONTHLY, YEARLY etc.

    // Area info
    private Double size;
    private String unit; // SQFT / SQM etc.

    private Integer bedrooms;
    private Integer bathrooms;

    // Address info
    private String houseNo;
    private String street;
    private String locality;
    private String city;
    private String state;
    private String country;
    private String pincode;

    private boolean isVerified;
    private String featuresSummary;

    // Images
    private List<Map<String, Object>> images; // id, url, isPrimary

    // Amenities
    private List<Map<String, Object>> amenities; // id, name
    private Set<String> tags;

    // Type-specific details
    private RentFlatDetailsDTO flatDetails;
    private RentPGDetailsDTO pgDetails;
    private RentCommercialDetailsDTO commercialDetails;

    private Instant createdAt;
    private Instant updatedAt;

}
