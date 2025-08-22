package com.example.RealEstateProjuct.dto;

import com.example.RealEstateProjuct.model.PropertyDetails.CommercialOfficeDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.FlatDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.HouseDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.PlotDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDTO {


    private Long id;
    private String title;
    private String propertyType;  // BUY / RENT
    private String category;      // FLAT / PLOT / HOUSE / COMMERCIAL / VILLA
    private String subtype;       // 3BHK, 2BHK, etc.

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

    private String status; // ACTIVE / INACTIVE
    private Instant publishedAt;
    private boolean isVerified;
    private String featuresSummary;

    // Images
    private List<Map<String, Object>> images; // id, url, isPrimary

    // Amenities
    private List<Map<String, Object>> amenities; // id, name

    // Tags
    private Set<String> tags;

    // Category-specific details
    private HouseDetails houseDetails;
    private FlatDetails flatDetails;
    private PlotDetails plotDetails;
    private CommercialOfficeDetails commercialOfficeDetails;

    private Instant createdAt;
    private Instant updatedAt;

}
