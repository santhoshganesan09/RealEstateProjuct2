package com.example.RealEstateProjuct.mapper.RentMapper;

import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentCommercialDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentPGDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RentPropertyMapper {

    public static Map<String, Object> toDTO(RentProperty property) {
        if (property == null) return null;

        Map<String, Object> dto = new HashMap<>();
        dto.put("id", property.getId());
        dto.put("title", property.getTitle());
        dto.put("propertyType", property.getPropertyType());

        // Owner as nested object
        if (property.getOwner() != null) {
            Map<String, Object> ownerMap = new HashMap<>();
            ownerMap.put("id", property.getOwner().getId());
            ownerMap.put("name", property.getOwner().getFullName());
            ownerMap.put("company_name", property.getOwner().getCompanyName());
            ownerMap.put("phone", property.getOwner().getPhone());
            dto.put("owner", ownerMap);
        }

        // RentPrice as nested map
        if (property.getRentPrice() != null) {
            Map<String, Object> rentMap = new HashMap<>();
            rentMap.put("amount", property.getRentPrice().getAmount());
            rentMap.put("currency", property.getRentPrice().getCurrency());
            rentMap.put("period", property.getRentPrice().getPeriod());
            dto.put("rentPrice", rentMap);
        }

        // Area
        if (property.getArea() != null) {
            Map<String, Object> areaMap = new HashMap<>();
            areaMap.put("size", property.getArea().getSize());
            areaMap.put("unit", property.getArea().getUnit());
            dto.put("area", areaMap);
        }

        dto.put("bedrooms", property.getBedrooms());
        dto.put("bathrooms", property.getBathrooms());

        // Address as nested object
        if (property.getAddress() != null) {
            Map<String, Object> addressMap = new HashMap<>();
            addressMap.put("house_no", property.getAddress().getHouseNo());
            addressMap.put("street", property.getAddress().getStreet());
            addressMap.put("locality", property.getAddress().getLocality());
            addressMap.put("city", property.getAddress().getCity());
            addressMap.put("state", property.getAddress().getState());
            addressMap.put("country", property.getAddress().getCountry());
            addressMap.put("pincode", property.getAddress().getPincode());
            dto.put("address", addressMap);
        }

        dto.put("isVerified", property.isVerified());
        dto.put("featuresSummary", property.getFeaturesSummary());

        // Images (only url for request, id + url for response)
        dto.put("images", property.getImages() != null ?
                property.getImages().stream().map(img -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("url", img.getUrl()); // match Buy property JSON
                    return map;
                }).collect(Collectors.toList()) : Collections.emptyList());

        // Amenities (only id in request)
        dto.put("amenities", property.getAmenities() != null ?
                property.getAmenities().stream().map(am -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", am.getId()); // match Buy property JSON request
                    return map;
                }).collect(Collectors.toList()) : Collections.emptyList());

        dto.put("tags", property.getTags());

        // Type-specific details
        if ("FLAT".equalsIgnoreCase(property.getPropertyType().name()) && property.getFlatDetails() != null) {
            dto.put("flatDetails", flatDetailsToMap(property.getFlatDetails()));
        } else if ("PG".equalsIgnoreCase(property.getPropertyType().name()) && property.getPgDetails() != null) {
            dto.put("pgDetails", pgDetailsToMap(property.getPgDetails()));
        } else if ("COMMERCIAL".equalsIgnoreCase(property.getPropertyType().name()) && property.getCommercialDetails() != null) {
            dto.put("commercialDetails", commercialDetailsToMap(property.getCommercialDetails()));
        }


        dto.put("createdAt", property.getCreatedAt());
        dto.put("updatedAt", property.getUpdatedAt());

        return dto;
    }

    private static Map<String, Object> flatDetailsToMap(RentFlatDetails details) {
        Map<String, Object> map = new HashMap<>();
        map.put("availableFor", details.getAvailableFor());
        map.put("furnishingStatus", details.getFurnishingStatus());
        map.put("ageOfProperty", details.getAgeOfProperty());
        map.put("floorNo", details.getFloorNo());
        map.put("totalFloors", details.getTotalFloors());
        map.put("balconies", details.getBalconies());
        map.put("amenities", details.getAmenities()); // just list of strings
        return map;
    }

    private static Map<String, Object> pgDetailsToMap(RentPGDetails details) {
        Map<String, Object> map = new HashMap<>();
        map.put("forBoysOrGirls", details.getForBoysOrGirls());
        map.put("availableFor", details.getAvailableFor());
        map.put("sharingType", details.getSharingType());
        map.put("pgType", details.getPgType());
        map.put("pgServices", details.getPgServices());
        map.put("furnishingStatus", details.getFurnishingStatus());
        map.put("totalCapacity", details.getTotalCapacity());
        map.put("bedrooms", details.getBedrooms());
        map.put("amenities", details.getAmenities());
        return map;
    }

    private static Map<String, Object> commercialDetailsToMap(RentCommercialDetails details) {
        Map<String, Object> map = new HashMap<>();
        map.put("seatCount", details.getSeatCount());
        map.put("officeSpecifications", details.getOfficeSpecifications());
        map.put("numberOfCabins", details.getNumberOfCabins());
        map.put("numberOfWashrooms", details.getNumberOfWashrooms());
        map.put("parkingAvailable", details.getParkingAvailable());
        map.put("facilities", details.getFacilities());
        map.put("floorPreference", details.getFloorPreference());
        map.put("locatedOn", details.getLocatedOn());
        map.put("officeSpreadOver", details.getOfficeSpreadOver());
        map.put("ageOfProperty", details.getAgeOfProperty());
        return map;
    }
}
