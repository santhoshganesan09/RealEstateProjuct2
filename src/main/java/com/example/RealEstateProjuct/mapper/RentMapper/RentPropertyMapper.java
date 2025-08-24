package com.example.RealEstateProjuct.mapper.RentMapper;

import com.example.RealEstateProjuct.dto.RentDTO.RentCommercialDetailsDTO;
import com.example.RealEstateProjuct.dto.RentDTO.RentPGDetailsDTO;
import com.example.RealEstateProjuct.dto.RentDTO.RentPropertyDTO;
import com.example.RealEstateProjuct.enumClass.AreaUnit;
import com.example.RealEstateProjuct.enumClass.RentProperty.RentPeriod;
import com.example.RealEstateProjuct.enumClass.RentProperty.RentPropertyType;
import com.example.RealEstateProjuct.model.*;
import com.example.RealEstateProjuct.model.RentProperty.RentPrice;
import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentCommercialDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import com.example.RealEstateProjuct.dto.RentDTO.RentFlatDetailsDTO;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentPGDetails;
import com.example.RealEstateProjuct.repo.AmenityRepo;
import com.example.RealEstateProjuct.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RentPropertyMapper {

    @Autowired
    private  CategoryRepo  categoryRepo;

    @Autowired
    private AmenityRepo  amenityRepo;

    public static RentPropertyDTO toDTO(RentProperty rentProperty) {
        if (rentProperty == null) return null;

        RentPropertyDTO.RentPropertyDTOBuilder builder = RentPropertyDTO.builder()
                .id(rentProperty.getId())
                .title(rentProperty.getTitle())
                .propertyType(rentProperty.getPropertyType() != null ? rentProperty.getPropertyType().name() : null)
                .category(rentProperty.getCategory() != null ? rentProperty.getCategory().getName() : null)
                .agent(toAgentMap(rentProperty.getOwner()))
                .price(toPriceMap(rentProperty.getRentPrice()))
                .area(toAreaMap(rentProperty.getArea()))
                .bedrooms(rentProperty.getBedrooms())
                .bathrooms(rentProperty.getBathrooms())
                .address(toAddressMap(rentProperty.getAddress()))
                .isVerified(rentProperty.isVerified())
                .featuresSummary(rentProperty.getFeaturesSummary())
                .images(toImageList(rentProperty.getImages()))
                .amenities(toAmenityList(rentProperty.getAmenities()))
                .tags(rentProperty.getTags())
                .createdAt(rentProperty.getCreatedAt())
                .updatedAt(rentProperty.getUpdatedAt());

        // Include only the relevant category-specific detail (mirrors PropertyMapper pattern)
        if (rentProperty.getCategory() != null) {
            String categoryName = rentProperty.getCategory().getName();
            if (categoryName != null) {
                switch (categoryName.toUpperCase()) {
                    case "FLAT":
                        builder.flatDetails(toFlatDetailsDTO(rentProperty.getFlatDetails()));
                        break;
                    case "PG":
                    case "PG_HOSTEL":
                        builder.pgDetails(toPGDetailsDTO(rentProperty.getPgDetails()));
                        break;
                    case "COMMERCIAL":
                        builder.commercialDetails(toCommercialDetailsDTO(rentProperty.getCommercialDetails()));
                        break;
                    default:
                        break;
                }
            }
        }

        return builder.build();
    }



    // ================= DTO -> ENTITY =================
    public  RentProperty toEntity(RentPropertyDTO dto) {
        if (dto == null) return null;

        RentProperty rentProperty = new RentProperty();
        rentProperty.setId(dto.getId());
        rentProperty.setTitle(dto.getTitle());
        rentProperty.setPropertyType(dto.getPropertyType() != null
                ? RentPropertyType.valueOf(dto.getPropertyType())
                : null);

        // Category
        if (dto.getCategory() != null) {
            Category category = categoryRepo.findByNameIgnoreCase(dto.getCategory())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + dto.getCategory()));
            rentProperty.setCategory(category);
        }


        rentProperty.setBedrooms(dto.getBedrooms());
        rentProperty.setBathrooms(dto.getBathrooms());
        rentProperty.setFeaturesSummary(dto.getFeaturesSummary());
        rentProperty.setVerified(dto.isVerified());

        // Address
        if (dto.getAddress() != null) {
            Address address = new Address();
            address.setHouseNo((String) dto.getAddress().get("house_no"));
            address.setStreet((String) dto.getAddress().get("street"));
            address.setLocality((String) dto.getAddress().get("locality"));
            address.setCity((String) dto.getAddress().get("city"));
            address.setState((String) dto.getAddress().get("state"));
            address.setCountry((String) dto.getAddress().get("country"));
            address.setPincode((String) dto.getAddress().get("pincode"));
            rentProperty.setAddress(address);
        }

        // Price
        if (dto.getPrice() != null) {
            RentPrice price = new RentPrice();
            price.setAmount(dto.getPrice().get("amount") != null
                    ? ((Number) dto.getPrice().get("amount")).doubleValue()
                    : null);
            price.setCurrency((String) dto.getPrice().get("currency"));
            price.setPeriod(dto.getPrice().get("type") != null
                    ? RentPeriod.valueOf((String) dto.getPrice().get("type"))
                    : null);
            rentProperty.setRentPrice(price);
        }

        // Area
        if (dto.getArea() != null) {
            Area area = new Area();
            area.setSize(dto.getArea().get("size") != null
                    ? ((Number) dto.getArea().get("size")).doubleValue()
                    : null);
            area.setUnit(dto.getArea().get("unit") != null
                    ? AreaUnit.valueOf((String) dto.getArea().get("unit"))
                    : null);
            rentProperty.setArea(area);
        }

        // Images
        if (dto.getImages() != null) {
            List<Image> images = dto.getImages().stream().map(imgMap -> {
                Image img = new Image();
                img.setId((Long) imgMap.get("id"));
                img.setUrl((String) imgMap.get("url"));
                img.setRentProperty(rentProperty);
                return img;
            }).collect(Collectors.toList());
            rentProperty.setImages(images);
        }

        // Amenities
//        if (dto.getAmenities() != null) {
//            Set<Amenity> amenities = dto.getAmenities().stream().map(am -> {
//                Amenity amenity = new Amenity();
//                amenity.setId((Long) am.get("id"));
//                amenity.setName((String) am.get("name"));
//                return amenity;
//            }).collect(Collectors.toSet());
//            rentProperty.setAmenities(amenities);
//        }

        // Amenities (COMMON for RentProperty)
        if (dto.getAmenities() != null) {
            Set<Amenity> amenities = dto.getAmenities().stream()
                    .map(am -> {
                        Long id = Long.valueOf(am.get("id").toString());
                        return amenityRepo.findById(id)
                                .orElseThrow(() -> new RuntimeException("Amenity not found: " + id));
                    })
                    .collect(Collectors.toSet());
            rentProperty.setAmenities(amenities);
        }


        // Tags
        if (dto.getTags() != null) {
            rentProperty.setTags(new HashSet<>(dto.getTags()));
        }

        // Flat Details
        if (dto.getFlatDetails() != null) {
            RentFlatDetails details = new RentFlatDetails();
            details.setId(dto.getFlatDetails().getId());
            details.setAvailableFor(dto.getFlatDetails().getAvailableFor());
            details.setFurnishingStatus(dto.getFlatDetails().getFurnishingStatus());
            details.setAgeOfProperty(dto.getFlatDetails().getAgeOfProperty());
            details.setFloorNo(dto.getFlatDetails().getFloorNo());
            details.setTotalFloors(dto.getFlatDetails().getTotalFloors());
            details.setBalconies(dto.getFlatDetails().getBalconies());

            details.setProperty(rentProperty);
            rentProperty.setFlatDetails(details);

            if (dto.getFlatDetails().getAmenities() != null) {
                Set<Amenity> flatAmenities = dto.getFlatDetails().getAmenities().stream()
                        .map(am -> {
                            Long id = Long.valueOf(am.get("id").toString());
                            return amenityRepo.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Amenity not found: " + id));
                        })
                        .collect(Collectors.toSet());
                details.setAmenities(flatAmenities);
            }
        }

        // PG Details
        if (dto.getPgDetails() != null) {
            RentPGDetails details = new RentPGDetails();
            details.setId(dto.getPgDetails().getId());
            details.setForBoysOrGirls(dto.getPgDetails().getForBoysOrGirls());
            details.setAvailableFor(dto.getPgDetails().getAvailableFor());
            details.setSharingType(dto.getPgDetails().getSharingType());
            details.setPgType(dto.getPgDetails().getPgType());
            details.setPgServices(dto.getPgDetails().getPgServices());
            details.setFurnishingStatus(dto.getPgDetails().getFurnishingStatus());
            details.setTotalCapacity(dto.getPgDetails().getTotalCapacity());
            details.setBedrooms(dto.getPgDetails().getBedrooms());

            details.setProperty(rentProperty);
            rentProperty.setPgDetails(details);

            // Map amenities from ID to name
            if (dto.getPgDetails().getAmenities() != null) {
                Set<Amenity> pgAmenities = dto.getPgDetails().getAmenities().stream()
                        .map(am -> {
                            Long id = Long.valueOf(am.get("id").toString());
                            return amenityRepo.findById(id)
                                    .orElseThrow(() -> new RuntimeException("Amenity not found: " + id));
                        })
                        .collect(Collectors.toSet());
                details.setAmenities(pgAmenities);
            }

        }

        // Commercial Details
        if (dto.getCommercialDetails() != null) {
            RentCommercialDetails details = new RentCommercialDetails();
            details.setId(dto.getCommercialDetails().getId());
            details.setSeatCount(dto.getCommercialDetails().getSeatCount());
            details.setOfficeSpecifications(dto.getCommercialDetails().getOfficeSpecifications());
            details.setNumberOfCabins(dto.getCommercialDetails().getNumberOfCabins());
            details.setNumberOfWashrooms(dto.getCommercialDetails().getNumberOfWashrooms());
            details.setParkingAvailable(dto.getCommercialDetails().getParkingAvailable());
            details.setFacilities(dto.getCommercialDetails().getFacilities());
            details.setFloorPreference(dto.getCommercialDetails().getFloorPreference());
            details.setLocatedOn(dto.getCommercialDetails().getLocatedOn());
            details.setOfficeSpreadOver(dto.getCommercialDetails().getOfficeSpreadOver());
            details.setAgeOfProperty(dto.getCommercialDetails().getAgeOfProperty());
            details.setProperty(rentProperty);
            rentProperty.setCommercialDetails(details);
        }

        return rentProperty;
    }






    // --- Helpers (same style as PropertyMapper) ---

    private static Map<String, Object> toAgentMap(User owner) {
        if (owner == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("id", owner.getId());
        map.put("name", owner.getFullName());
        map.put("company_name", owner.getCompanyName());
        map.put("phone", owner.getPhone());
        return map;
    }

    private static Map<String, Object> toPriceMap(RentPrice price) {
        if (price == null) return null;
        Map<String, Object> map = new HashMap<>();
        map.put("amount", price.getAmount());
        map.put("currency", price.getCurrency());
        // DTO expects "type" like in PropertyDTO; here it's RentPrice.period
        map.put("type", price.getPeriod() != null ? price.getPeriod().name() : null);
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
            // If you later add a boolean 'primary' flag to Image, include it here:
            // map.put("isPrimary", img.isPrimary());
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

    // --- Category-specific DTO Mappers ---

    private static RentFlatDetailsDTO toFlatDetailsDTO(RentFlatDetails details) {
        if (details == null) return null;

        Set<Map<String, Object>> amenities = details.getAmenities() != null
                ? details.getAmenities().stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", a.getId());
                    map.put("name", a.getName());
                    return map;
                })
                .collect(Collectors.toSet()) // <-- use Set instead of List
                : Collections.emptySet();


        return RentFlatDetailsDTO.builder()
                .id(details.getId())
                .availableFor(details.getAvailableFor())
                .furnishingStatus(details.getFurnishingStatus())
                .ageOfProperty(details.getAgeOfProperty())
                .floorNo(details.getFloorNo())
                .totalFloors(details.getTotalFloors())
                .balconies(details.getBalconies())
                .amenities(amenities)
                .build();
    }

    private static RentPGDetailsDTO toPGDetailsDTO(RentPGDetails details) {
        if (details == null) return null;


        Set<Map<String, Object>> amenities = details.getAmenities() != null
                ? details.getAmenities().stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", a.getId());
                    map.put("name", a.getName());
                    return map;
                })
                .collect(Collectors.toSet()) // <-- use Set instead of List
                : Collections.emptySet();


        return RentPGDetailsDTO.builder()
                .id(details.getId())
                .forBoysOrGirls(details.getForBoysOrGirls())
                .availableFor(details.getAvailableFor())
                .sharingType(details.getSharingType())
                .pgType(details.getPgType())
                .pgServices(details.getPgServices())
                .furnishingStatus(details.getFurnishingStatus())
                .totalCapacity(details.getTotalCapacity())
                .bedrooms(details.getBedrooms())
                .amenities(amenities)
                .build();
    }

    private static RentCommercialDetailsDTO toCommercialDetailsDTO(RentCommercialDetails details) {
        if (details == null) return null;
        return RentCommercialDetailsDTO.builder()
                .id(details.getId())
                .seatCount(details.getSeatCount())
                .officeSpecifications(details.getOfficeSpecifications())
                .numberOfCabins(details.getNumberOfCabins())
                .numberOfWashrooms(details.getNumberOfWashrooms())
                .parkingAvailable(details.getParkingAvailable())
                .facilities(details.getFacilities())
                .floorPreference(details.getFloorPreference())
                .locatedOn(details.getLocatedOn())
                .officeSpreadOver(details.getOfficeSpreadOver())
                .ageOfProperty(details.getAgeOfProperty())
                .build();
    }

}
