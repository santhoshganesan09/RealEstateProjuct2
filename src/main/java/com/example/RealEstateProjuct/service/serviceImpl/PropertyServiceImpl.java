package com.example.RealEstateProjuct.service.serviceImpl;

import com.example.RealEstateProjuct.dto.*;
import com.example.RealEstateProjuct.enumClass.*;
import com.example.RealEstateProjuct.mapper.PropertyMapper;
import com.example.RealEstateProjuct.model.*;
import com.example.RealEstateProjuct.model.PropertyDetails.CommercialOfficeDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.FlatDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.HouseDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.PlotDetails;
import com.example.RealEstateProjuct.repo.*;
import com.example.RealEstateProjuct.service.PropertyService;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepo propertyRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final AmenityRepo amenityRepo;
    private final ImageRepo imageRepo;

    @Override
    public PropertyDTO createProperty(PropertyDTO dto) {
        Property property = new Property();
        mapDtoToEntity(dto, property);

        Property saved = propertyRepo.save(property);
        return PropertyMapper.toDTO(saved);
    }

    @Override
    public PropertyDTO getPropertyById(Long id) {
        Property property = propertyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
        return PropertyMapper.toDTO(property);
    }

    @Override
    public List<PropertyDTO> getAllProperties() {
        return propertyRepo.findAll()
                .stream()
                .map(PropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDTO updateProperty(Long id, PropertyDTO dto) {
        Property property = propertyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));

        mapDtoToEntity(dto, property);

        Property updated = propertyRepo.save(property);
        return PropertyMapper.toDTO(updated);
    }

    @Override
    public void deleteProperty(Long id) {
        Property property = propertyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + id));
        propertyRepo.delete(property);
    }




    /**
     * Maps a PropertyDTO to a Property entity (for create or update)
     */
    private void mapDtoToEntity(PropertyDTO dto, Property property) {
        property.setTitle(dto.getTitle());
        property.setPropertyType(dto.getPropertyType() != null ? Enum.valueOf(PropertyType.class, dto.getPropertyType()) : property.getPropertyType());
        property.setSubtype(dto.getSubtype());
        property.setBedrooms(dto.getBedrooms());
        property.setBathrooms(dto.getBathrooms());
        property.setFeaturesSummary(dto.getFeaturesSummary());
        property.setVerified(dto.isVerified());
        property.setStatus(dto.getStatus() != null ? Enum.valueOf(PropertyStatus.class, dto.getStatus()) : property.getStatus());
        property.setPublishedAt(dto.getPublishedAt());
        property.setUpdatedAt(Instant.now());
        property.setCreatedAt(property.getCreatedAt() != null ? property.getCreatedAt() : Instant.now());

        // Owner (agent)
        if (dto.getAgent() != null && dto.getAgent().get("id") != null) {
            Long ownerId = Long.valueOf(dto.getAgent().get("id").toString());
            User owner = userRepo.findById(ownerId)
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            property.setOwner(owner);
        }

        // Category
        if (dto.getCategory() != null) {
            Category category = categoryRepo.findByName(dto.getCategory())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            property.setCategory(category);
        }

        // Embedded: Price
        if (dto.getPrice() != null) {
            Price price = new Price();

            // Convert amount to Double
            price.setAmount(dto.getPrice().get("amount") != null
                    ? Double.valueOf(dto.getPrice().get("amount").toString())
                    : null);

            // Convert string to CurrencyType enum
            if (dto.getPrice().get("currency") != null) {
                price.setCurrency(CurrencyType.valueOf(dto.getPrice().get("currency").toString().toUpperCase()));
            }

            // Convert string to PriceType enum
            if (dto.getPrice().get("type") != null) {
                price.setType(PriceType.valueOf(dto.getPrice().get("type").toString().toUpperCase()));
            }

            property.setPrice(price);
        }


        // Embedded: Area
        if (dto.getArea() != null) {
            Area area = new Area();
            area.setSize(dto.getArea().get("size") != null
                    ? Double.valueOf(dto.getArea().get("size").toString())
                    : null);

            if (dto.getArea().get("unit") != null) {
                area.setUnit(AreaUnit.valueOf(dto.getArea().get("unit").toString().toUpperCase()));
            }

            property.setArea(area);
        }




        // Embedded: Address
        if (dto.getAddress() != null) {
            Address address = new Address();

            // accept both "houseNo" (your JSON) and "house_no" (if any existing clients)
            Object houseNo = dto.getAddress().get("houseNo");
            if (houseNo == null) houseNo = dto.getAddress().get("house_no");
            address.setHouseNo(houseNo != null ? houseNo.toString() : null);


            address.setStreet(dto.getAddress().get("street") != null ? dto.getAddress().get("street").toString() : null);
            address.setLocality(dto.getAddress().get("locality") != null ? dto.getAddress().get("locality").toString() : null);
            address.setCity(dto.getAddress().get("city") != null ? dto.getAddress().get("city").toString() : null);
            address.setState(dto.getAddress().get("state") != null ? dto.getAddress().get("state").toString() : null);
            address.setCountry(dto.getAddress().get("country") != null ? dto.getAddress().get("country").toString() : null);
            address.setPincode(dto.getAddress().get("pincode") != null ? dto.getAddress().get("pincode").toString() : null);
            property.setAddress(address);
        }



        // --- Clear unrelated details when category changes (optional but safer) ---
        String cat = property.getCategory() != null ? property.getCategory().getName().toUpperCase() : "";
        if (!"FLAT".equals(cat))    property.setFlatDetails(null);
        if (!"HOUSE".equals(cat) && !"VILLA".equals(cat)) property.setHouseDetails(null);
        if (!"PLOT".equals(cat))    property.setPlotDetails(null);
        if (!"COMMERCIAL".equals(cat)) property.setCommercialOfficeDetails(null);

        // --- Category-specific details (DTOs -> Entities) ---
        switch (cat) {
            case "FLAT":
                if (dto.getFlatDetails() != null) {
                    FlatDetails f = dto.getFlatDetails();
                    FlatDetails flat = property.getFlatDetails();
                    if (flat == null) flat = new FlatDetails();

                    flat.setBedrooms(f.getBedrooms());
                    flat.setBathrooms(f.getBathrooms());
                    flat.setFloorNo(f.getFloorNo());
                    flat.setTotalFloors(f.getTotalFloors());
                    flat.setFurnishingStatus(f.getFurnishingStatus());
                    flat.setBalconies(f.getBalconies());

                    flat.setProperty(property);
                    property.setFlatDetails(flat);
                }
                break;

            case "HOUSE":
            case "VILLA":
                if (dto.getHouseDetails() != null) {
                    HouseDetails h = dto.getHouseDetails();
                    HouseDetails house = property.getHouseDetails();
                    if (house == null) house = new HouseDetails();

                    house.setBedrooms(h.getBedrooms());
                    house.setBathrooms(h.getBathrooms());
                    house.setFurnishingStatus(h.getFurnishingStatus());
                    house.setCarParking(h.getCarParking());
                    house.setTotalFloors(h.getTotalFloors());
                    house.setPlotArea(h.getPlotArea());
                    house.setPrivateGarden(h.getPrivateGarden());
                    house.setParkingAvailable(h.getParkingAvailable());

                    house.setProperty(property);
                    property.setHouseDetails(house);
                }
                break;

            case "PLOT":
                if (dto.getPlotDetails() != null) {
                    PlotDetails p = dto.getPlotDetails();
                    PlotDetails plot = property.getPlotDetails();
                    if (plot == null) plot = new PlotDetails();

                    plot.setPlotSize(p.getPlotSize());
                    plot.setLandType(p.getLandType());
                    plot.setZoningType(p.getZoningType());
                    plot.setFacing(p.getFacing());
                    plot.setRoadWidth(p.getRoadWidth());
                    plot.setBoundaryWall(p.getBoundaryWall());
                    // p.getPropertyId() is NOT required on create; we link by setProperty:
                    plot.setProperty(property);

                    property.setPlotDetails(plot);
                }
                break;

            case "COMMERCIAL":
                if (dto.getCommercialOfficeDetails() != null) {
                    CommercialOfficeDetails c = dto.getCommercialOfficeDetails();
                    CommercialOfficeDetails office = property.getCommercialOfficeDetails();
                    if (office == null) office = new CommercialOfficeDetails();

                    office.setOfficeType(c.getOfficeType());
                    office.setCarpetArea(c.getCarpetArea());
                    office.setFurnishingStatus(c.getFurnishingStatus());
                    office.setParkingSpaces(c.getParkingSpaces());
                    office.setConferenceRooms(c.getConferenceRooms());
                    office.setWorkstations(c.getWorkstations());
                    office.setReceptionArea(c.isReceptionArea());
                    office.setFloorNumber(c.getFloorNumber());
                    office.setTotalFloor(c.getTotalFloor());
                    office.setPowerBackup(c.isPowerBackup());
                    office.setCafeteria(c.isCafeteria());

                    office.setProperty(property);
                    property.setCommercialOfficeDetails(office);
                }
                break;

            default:
                // do nothing
                break;
        }



        // Images
        if (dto.getImages() != null) {
            List<Image> images = dto.getImages().stream().map(imgMap -> {
                Image img = new Image();
                img.setUrl(imgMap.get("url") != null ? imgMap.get("url").toString() : null);
                img.setProperty(property);
                img.setRentProperty(null);
                return img;
            }).collect(Collectors.toList());
            property.setImages(images);
        }

        // Amenities
        if (dto.getAmenities() != null) {
            Set<Amenity> amenities = dto.getAmenities().stream()
                    .map(a -> amenityRepo.findById(Long.valueOf(a.get("id").toString()))
                            .orElseThrow(() -> new RuntimeException("Amenity not found")))
                    .collect(Collectors.toSet());
            property.setAmenities(amenities);
        }

        // Tags
        if (dto.getTags() != null) {
            property.setTags(new HashSet<>(dto.getTags()));
        }
    }

    /**
     * Filter properties by multiple criteria:
     * category, price range, amenities, createdByRole, verified, location, city
     */
    public List<PropertyDTO> filterProperties(String category,
                                              Double minPrice,
                                              Double maxPrice,
                                              String amenity,
                                              String createdByRole,
                                              String subtype,
                                              Boolean verified,
                                              String location,
                                              String city)  {

        Specification<Property> spec = Specification.allOf();

        // --- Category filter ---
        if (category != null && !category.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(cb.lower(root.get("category").get("name")), category.toLowerCase()));
        }

//        // Subtype filter
//        if (subtype != null && !subtype.isEmpty()) {
//            spec = spec.and((root, query, cb) ->
//                    cb.equal(cb.lower(root.get("subtype")), subtype.toLowerCase().trim()));
//        }

        // Subtype filter (trim DB value + input, exclude nulls and empty)
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
            spec = spec.and((root, query, cb) ->
                    cb.ge(root.get("price").get("amount"), minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) ->
                    cb.le(root.get("price").get("amount"), maxPrice));
        }

        // --- Amenity filter ---
        // Amenity filter (matches by name)
        if (amenity != null && !amenity.isEmpty()) {
            spec = spec.and((root, query, cb) -> {
                Join<Property, Amenity> amenities = root.join("amenities");
                return cb.equal(cb.lower(amenities.get("name")), amenity.toLowerCase());
            });
        }


        // --- CreatedByRole filter (OWNER / AGENT) ---
        if (createdByRole != null && !createdByRole.isEmpty()) {
            if (createdByRole.equalsIgnoreCase("OWNER")) {
                spec = spec.and((root, query, cb) -> cb.isNotNull(root.get("owner")));
            } else if (createdByRole.equalsIgnoreCase("AGENT")) {
                spec = spec.and((root, query, cb) -> cb.isNotNull(root.get("agent")));
            }
        }

        // --- Verified filter ---
        if (verified != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isVerified"), verified));
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

        return propertyRepo.findAll(spec)
                .stream()
                .map(PropertyMapper::toDTO)
                .collect(Collectors.toList());
    }




}
