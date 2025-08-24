package com.example.RealEstateProjuct.service.serviceImpl;


import com.example.RealEstateProjuct.dto.RentDTO.RentPropertyDTO;
import com.example.RealEstateProjuct.enumClass.AreaUnit;
import com.example.RealEstateProjuct.enumClass.PropertyType;
import com.example.RealEstateProjuct.enumClass.RentProperty.RentPeriod;
import com.example.RealEstateProjuct.enumClass.RentProperty.RentPropertyType;
import com.example.RealEstateProjuct.mapper.RentMapper.RentDetailsMapper;
import com.example.RealEstateProjuct.mapper.RentMapper.RentPropertyMapper;
import com.example.RealEstateProjuct.model.*;
import com.example.RealEstateProjuct.model.RentProperty.RentPrice;
import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentCommercialDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentPGDetails;
import com.example.RealEstateProjuct.repo.*;
import com.example.RealEstateProjuct.repo.rentRepo.RentPropertyRepo;
import com.example.RealEstateProjuct.service.RentPropertyService;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;



@Service
@Transactional
@RequiredArgsConstructor
public class RentPropertyServiceImpl implements RentPropertyService {

    private final RentPropertyRepo rentPropertyRepository;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final AmenityRepo amenityRepo;
    private final ImageRepo imageRepo;
    private final RentDetailsMapper rentDetailsMapper;


    @Override
    public RentPropertyDTO createRentProperty(RentPropertyDTO dto) {
        RentProperty property = new RentProperty();
        mapDtoToEntity(dto, property);

        // Map child details without amenities
        if (dto.getPgDetails() != null) {
            RentPGDetails pg = rentDetailsMapper.mapPgDetails(dto.getPgDetails());
            pg.setProperty(property);
            property.setPgDetails(pg);
        }

        if (dto.getFlatDetails() != null) {
            RentFlatDetails flat = rentDetailsMapper.mapFlatDetails(dto.getFlatDetails());
            flat.setProperty(property);
            property.setFlatDetails(flat);
        }

        if (dto.getCommercialDetails() != null) {
            RentCommercialDetails commercial = rentDetailsMapper.mapCommercialDetails(dto.getCommercialDetails());
            commercial.setProperty(property);
            property.setCommercialDetails(commercial);
        }


        // ✅ Save parent property with children but without amenities
        RentProperty savedProperty = rentPropertyRepository.saveAndFlush(property);

        // Now set amenities after IDs exist
        if (savedProperty.getPgDetails() != null && dto.getPgDetails() != null
                && dto.getPgDetails().getAmenities() != null) {

            Set<Amenity> pgAmenities = dto.getPgDetails().getAmenities().stream()
                    .map(am -> amenityRepo.findById(Long.valueOf(am.get("id").toString()))
                            .orElseThrow(() -> new RuntimeException("Amenity not found: " + am.get("id"))))
                    .collect(Collectors.toSet());

            savedProperty.getPgDetails().setAmenities(pgAmenities);
        }


        if (savedProperty.getFlatDetails() != null && dto.getFlatDetails() != null
                && dto.getFlatDetails().getAmenities() != null) {

            Set<Amenity> flatAmenities = dto.getFlatDetails().getAmenities().stream()
                    .map(am -> amenityRepo.findById(Long.valueOf(am.get("id").toString()))
                            .orElseThrow(() -> new RuntimeException("Amenity not found: " + am.get("id"))))
                    .collect(Collectors.toSet());

            savedProperty.getFlatDetails().setAmenities(flatAmenities);
        }


        // Save again to persist amenities
        savedProperty = rentPropertyRepository.save(savedProperty);

        return RentPropertyMapper.toDTO(savedProperty);
    }


    @Override
    public RentPropertyDTO getRentPropertyById(Long id) {
        RentProperty property = rentPropertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RentProperty not found with ID: " + id));
        return RentPropertyMapper.toDTO(property);
    }

    @Override
    public List<RentPropertyDTO> getAllRentProperties() {
        return rentPropertyRepository.findAll()
                .stream()
                .map(RentPropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RentPropertyDTO updateRentProperty(Long id, RentPropertyDTO dto) {
        RentProperty property = rentPropertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RentProperty not found with ID: " + id));

        // Map DTO to entity
        mapDtoToEntity(dto, property);

        // Save main property first to generate IDs for Flat/PG details if new
        RentProperty savedProperty = rentPropertyRepository.save(property);

        // Persist Flat amenities if present
        if (savedProperty.getFlatDetails() != null && savedProperty.getFlatDetails().getAmenities() != null) {
            savedProperty.getFlatDetails().setAmenities(savedProperty.getFlatDetails().getAmenities());
        }

        // Persist PG amenities if present
        if (savedProperty.getPgDetails() != null && savedProperty.getPgDetails().getAmenities() != null) {
            savedProperty.getPgDetails().setAmenities(savedProperty.getPgDetails().getAmenities());
        }

        // Save again to persist Many-to-Many join tables
        savedProperty = rentPropertyRepository.save(savedProperty);

        return RentPropertyMapper.toDTO(savedProperty);
    }

    @Override
    public void deleteRentProperty(Long id) {
        RentProperty property = rentPropertyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("RentProperty not found with ID: " + id));
        rentPropertyRepository.delete(property);
    }

    @Override
    public List<RentPropertyDTO> filterRentProperties(String category,
                                                      Double minPrice,
                                                      Double maxPrice,
                                                      String amenity,
                                                      String createdByRole,
                                                      String subtype,
                                                      Boolean verified,
                                                      String location,
                                                      String city) {

        Specification<RentProperty> spec = Specification.allOf();

        // --- Category filter ---
        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("category").get("name")), category.toLowerCase()));
        }

        // --- Subtype filter ---
        if (subtype != null && !subtype.trim().isEmpty() && !"null".equalsIgnoreCase(subtype.trim())) {
            String subTypeTrimmed = subtype.trim().toLowerCase();
            spec = spec.and((root, query, cb) ->
                    cb.and(
                            cb.isNotNull(root.get("subtype")),
                            cb.notEqual(cb.trim(cb.lower(root.get("subtype"))), ""),
                            cb.equal(cb.lower(cb.trim(root.get("subtype"))), subTypeTrimmed)
                    )
            );
        }

        // --- Price range filter ---
        if (minPrice != null) {
            spec = spec.and((root, query, cb) -> cb.ge(root.get("rentPrice").get("amount"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.le(root.get("rentPrice").get("amount"), maxPrice));
        }

        // --- Amenity filter ---
        if (amenity != null && !amenity.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                Join<RentProperty, Amenity> amenities = root.join("amenities");
                return cb.equal(cb.lower(amenities.get("name")), amenity.toLowerCase());
            });
        }

        // --- CreatedByRole filter ---
        if (createdByRole != null && !createdByRole.isEmpty()) {
            if (createdByRole.equalsIgnoreCase("OWNER")) {
                spec = spec.and((root, query, cb) -> cb.isNotNull(root.get("owner")));
            } else if (createdByRole.equalsIgnoreCase("AGENT")) {
                spec = spec.and((root, query, cb) -> cb.isNotNull(root.get("agent")));
            }
        }

        // --- Verified filter ---
        if (verified != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("verified"), verified));
        }

        // --- Location filter ---
        if (location != null && !location.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("address").get("locality")), "%" + location.toLowerCase() + "%"));
        }

        // --- City filter ---
        if (city != null && !city.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("address").get("city")), "%" + city.toLowerCase() + "%"));
        }

        return rentPropertyRepository.findAll(spec)
                .stream()
                .map(RentPropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Map RentPropertyDTO to RentProperty entity (for create or update)
     */
    private void mapDtoToEntity(RentPropertyDTO dto, RentProperty property) {
        property.setTitle(dto.getTitle());
        // propertyType conversion (String -> Enum)
        if (dto.getPropertyType() != null) {
            property.setPropertyType(RentPropertyType.valueOf(dto.getPropertyType().toUpperCase()));
        }

        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setFeaturesSummary(dto.getFeaturesSummary());
        property.setVerified(dto.isVerified());
        property.setTags(dto.getTags() != null ? new HashSet<>(dto.getTags()) : null);

        // Category
        if (dto.getCategory() != null) {
            Category category = categoryRepo.findByName(dto.getCategory())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            property.setCategory(category);
        }

        // Owner (Agent map contains ID)
        if (dto.getAgent() != null && dto.getAgent().get("id") != null) {
            Long ownerId = Long.parseLong(dto.getAgent().get("id").toString());
            User owner = userRepo.findById(ownerId)
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            property.setOwner(owner);
        }


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
            property.setAddress(address);
        }

        // Price
        if (dto.getPrice() != null) {
            RentPrice price = new RentPrice();
            if (dto.getPrice().get("amount") != null) {
                price.setAmount(((Number) dto.getPrice().get("amount")).doubleValue());
            }
            price.setCurrency((String) dto.getPrice().get("currency"));
            if (dto.getPrice().get("type") != null) {
                price.setPeriod(RentPeriod.valueOf(((String) dto.getPrice().get("type")).toUpperCase()));
            }
            property.setRentPrice(price);
        }

        // Area
        if (dto.getArea() != null) {
            Area area = new Area();
            if (dto.getArea().get("size") != null) {
                area.setSize(((Number) dto.getArea().get("size")).doubleValue());
            }
            if (dto.getArea().get("unit") != null) {
                area.setUnit(AreaUnit.valueOf(((String) dto.getArea().get("unit")).toUpperCase()));
            }
            property.setArea(area);
        }

        // Images
        if (dto.getImages() != null) {
            RentProperty finalProperty = property;
            List<Image> images = dto.getImages().stream().map(imgMap -> {
                Image img = new Image();
                img.setUrl(imgMap.get("url") != null ? imgMap.get("url").toString() : null);
                img.setRentProperty(finalProperty);
                img.setProperty(null);
                return img;
            }).collect(Collectors.toList());
            property.setImages(images);
        }


        // Amenities
        if (dto.getAmenities() != null) {
            Set<Amenity> amenities = dto.getAmenities().stream()
                    .map(map -> amenityRepo.findById(Long.valueOf(map.get("id").toString()))
                            .orElseThrow(() -> new RuntimeException("Amenity not found")))
                    .collect(Collectors.toSet());
            property.setAmenities(amenities);
        }



        // Flat details
        if (dto.getFlatDetails() != null) {
            RentFlatDetails flat = new RentFlatDetails();
            flat.setAvailableFor(dto.getFlatDetails().getAvailableFor());
            flat.setFurnishingStatus(dto.getFlatDetails().getFurnishingStatus());
            flat.setAgeOfProperty(dto.getFlatDetails().getAgeOfProperty());
            flat.setFloorNo(dto.getFlatDetails().getFloorNo());
            flat.setTotalFloors(dto.getFlatDetails().getTotalFloors());
            flat.setBalconies(dto.getFlatDetails().getBalconies());
            flat.setProperty(property);

            // Flat amenities
            if (dto.getFlatDetails().getAmenities() != null) {
                Set<Amenity> flatAmenities = dto.getFlatDetails().getAmenities().stream()
                        .map(am -> amenityRepo.findById(Long.valueOf(am.get("id").toString()))
                                .orElseThrow(() -> new RuntimeException("Amenity not found")))
                        .collect(Collectors.toSet());
                flat.setAmenities(flatAmenities);
            }


            property.setFlatDetails(flat);
        }

        // PG details
        if (dto.getPgDetails() != null) {
            RentPGDetails pg = new RentPGDetails();
            pg.setForBoysOrGirls(dto.getPgDetails().getForBoysOrGirls());
            pg.setAvailableFor(dto.getPgDetails().getAvailableFor());
            pg.setSharingType(dto.getPgDetails().getSharingType());
            pg.setPgType(dto.getPgDetails().getPgType());
            pg.setPgServices(dto.getPgDetails().getPgServices());
            pg.setFurnishingStatus(dto.getPgDetails().getFurnishingStatus());
            pg.setTotalCapacity(dto.getPgDetails().getTotalCapacity());
            pg.setBedrooms(dto.getPgDetails().getBedrooms());
            pg.setProperty(property);

            // PG amenities
            if (dto.getPgDetails().getAmenities() != null) {
                Set<Amenity> pgAmenities = dto.getPgDetails().getAmenities().stream()
                        .map(am -> amenityRepo.findById(Long.valueOf(am.get("id").toString()))
                                .orElseThrow(() -> new RuntimeException("Amenity not found")))
                        .collect(Collectors.toSet());
                pg.setAmenities(pgAmenities);
            }

            property.setPgDetails(pg);


        }


            // Commercial details
            if (dto.getCommercialDetails() != null) {
                RentCommercialDetails com = new RentCommercialDetails();
                com.setSeatCount(dto.getCommercialDetails().getSeatCount());
                com.setOfficeSpecifications(dto.getCommercialDetails().getOfficeSpecifications());
                com.setNumberOfCabins(dto.getCommercialDetails().getNumberOfCabins());
                com.setNumberOfWashrooms(dto.getCommercialDetails().getNumberOfWashrooms());
                com.setParkingAvailable(dto.getCommercialDetails().getParkingAvailable());
                com.setFacilities(dto.getCommercialDetails().getFacilities());
                com.setFloorPreference(dto.getCommercialDetails().getFloorPreference());
                com.setLocatedOn(dto.getCommercialDetails().getLocatedOn());
                com.setOfficeSpreadOver(dto.getCommercialDetails().getOfficeSpreadOver());
                com.setAgeOfProperty(dto.getCommercialDetails().getAgeOfProperty());
                com.setProperty(property);
                property.setCommercialDetails(com);
            }

            // Address and RentPrice mapping can be added similarly
        }


    }

