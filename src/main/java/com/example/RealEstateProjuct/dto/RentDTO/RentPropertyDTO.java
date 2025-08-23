package com.example.RealEstateProjuct.dto.RentDTO;

import com.example.RealEstateProjuct.enumClass.RentProperty.RentPropertyType;
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

    private String category;

    // Owner/Agent info
    private Map<String, Object> agent; // id, name, companyName, phone


    // Price info
    private Map<String, Object> price; // amount, currency, type

    // Area info
    private Map<String, Object> area; // size, unit

    private Integer bedrooms;
    private Integer bathrooms;

    // Address info
    private Map<String, Object> address; // houseNo, street, locality, city, state, country, pincode

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
