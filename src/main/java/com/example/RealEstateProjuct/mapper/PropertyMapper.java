package com.example.RealEstateProjuct.mapper;

import com.example.RealEstateProjuct.dto.PropertyDTO;
import com.example.RealEstateProjuct.model.*;


import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyMapper {

    public static PropertyDTO toDTO(Property property) {
        if (property == null) return null;

        PropertyDTO.PropertyDTOBuilder builder = PropertyDTO.builder()
                .id(property.getId())
                .title(property.getTitle())
                .propertyType(property.getPropertyType().name())
                .category(property.getCategory() != null ? property.getCategory().getName() : null)
                .subtype(property.getSubtype())
                .agent(toAgentMap(property.getOwner()))
                .price(toPriceMap(property.getPrice()))
                .area(toAreaMap(property.getArea()))
                .bedrooms(property.getBedrooms())
                .bathrooms(property.getBathrooms())
                .address(toAddressMap(property.getAddress()))
                .status(property.getStatus() != null ? property.getStatus().name() : null)
                .publishedAt(property.getPublishedAt())
                .isVerified(property.isVerified())
                .featuresSummary(property.getFeaturesSummary())
                .images(toImageList(property.getImages()))
                .amenities(toAmenityList(property.getAmenities()))
                .tags(property.getTags())
                .createdAt(property.getCreatedAt())
                .updatedAt(property.getUpdatedAt());

        // Include only the relevant category-specific detail
        if (property.getCategory() != null) {
            String categoryName = property.getCategory().getName().toUpperCase();
            switch (categoryName) {
                case "FLAT":
                    builder.flatDetails(property.getFlatDetails());
                    break;
                case "HOUSE":
                case "VILLA":
                    builder.houseDetails(property.getHouseDetails());
                    break;
                case "PLOT":
                    builder.plotDetails(property.getPlotDetails());
                    break;
                case "COMMERCIAL":
                    builder.commercialOfficeDetails(property.getCommercialOfficeDetails());
                    break;
                default:
                    // no category-specific detail
                    break;
            }
        }

        return builder.build();
    }

    // --- Mapping helpers ---

    private static Map<String, Object> toAgentMap(User owner) {
        if (owner == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("id", owner.getId());
        map.put("name", owner.getFullName());
        map.put("company_name", owner.getCompanyName());
        map.put("phone", owner.getPhone());
        return map;
    }

    private static Map<String, Object> toPriceMap(Price price) {
        if (price == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("amount", price.getAmount());
        map.put("currency", price.getCurrency());
        map.put("type", price.getType());
        return map;
    }

    private static Map<String, Object> toAreaMap(Area area) {
        if (area == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("size", area.getSize());
        map.put("unit", area.getUnit());
        return map;
    }

    private static Map<String, Object> toAddressMap(Address address) {
        if (address == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("house_no", address.getHouseNo());
        map.put("street", address.getStreet());
        map.put("locality", address.getLocality());
        map.put("city", address.getCity());
        map.put("state", address.getState());
        map.put("country", address.getCountry());
        map.put("pincode", address.getPincode());
        return map;
    }

    private static List<Map<String, Object>> toImageList(List<Image> images) {
        if (images == null) return Collections.emptyList();
        return images.stream().map(img -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", img.getId());
            map.put("url", img.getUrl());

            return map;
        }).collect(Collectors.toList());
    }

    private static List<Map<String, Object>> toAmenityList(Set<Amenity> amenities) {
        if (amenities == null) return Collections.emptyList();
        return amenities.stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("name", a.getName());
            return map;
        }).collect(Collectors.toList());
    }

}
