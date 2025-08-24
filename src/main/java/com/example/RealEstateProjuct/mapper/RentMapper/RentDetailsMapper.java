package com.example.RealEstateProjuct.mapper.RentMapper;

import com.example.RealEstateProjuct.dto.RentDTO.RentCommercialDetailsDTO;
import com.example.RealEstateProjuct.dto.RentDTO.RentFlatDetailsDTO;
import com.example.RealEstateProjuct.dto.RentDTO.RentPGDetailsDTO;
import com.example.RealEstateProjuct.model.Amenity;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentCommercialDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentPGDetails;
import com.example.RealEstateProjuct.repo.AmenityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RentDetailsMapper {


    private final AmenityRepo amenityRepo;

    public RentPGDetails mapPgDetails(RentPGDetailsDTO dto) {
        RentPGDetails pg = new RentPGDetails();
        pg.setForBoysOrGirls(dto.getForBoysOrGirls());
        pg.setAvailableFor(dto.getAvailableFor());
        pg.setSharingType(dto.getSharingType());
        pg.setPgType(dto.getPgType());
        pg.setPgServices(dto.getPgServices());
        pg.setFurnishingStatus(dto.getFurnishingStatus());
        pg.setTotalCapacity(dto.getTotalCapacity());
        pg.setBedrooms(dto.getBedrooms());

//        if (dto.getAmenities() != null) {
//            Set<Amenity> amenities = dto.getAmenities().stream()
//                    .map(am -> amenityRepo.findById(Long.valueOf(am.get("id").toString()))
//                            .orElseThrow(() -> new RuntimeException("Amenity not found: " + am.get("id"))))
//                    .collect(Collectors.toSet());
//            pg.setAmenities(amenities);
//        }

        return pg;
    }

    public RentFlatDetails mapFlatDetails(RentFlatDetailsDTO dto) {
        RentFlatDetails flat = new RentFlatDetails();
        flat.setAvailableFor(dto.getAvailableFor());
        flat.setFurnishingStatus(dto.getFurnishingStatus());
        flat.setAgeOfProperty(dto.getAgeOfProperty());
        flat.setFloorNo(dto.getFloorNo());
        flat.setTotalFloors(dto.getTotalFloors());
        flat.setBalconies(dto.getBalconies());

//        if (dto.getAmenities() != null) {
//            Set<Amenity> amenities = dto.getAmenities().stream()
//                    .map(am -> amenityRepo.findById(Long.valueOf(am.get("id").toString()))
//                            .orElseThrow(() -> new RuntimeException("Amenity not found: " + am.get("id"))))
//                    .collect(Collectors.toSet());
//            flat.setAmenities(amenities);
//        }

        return flat;
    }

    public RentCommercialDetails mapCommercialDetails(RentCommercialDetailsDTO dto) {
        RentCommercialDetails com = new RentCommercialDetails();
        com.setSeatCount(dto.getSeatCount());
        com.setOfficeSpecifications(dto.getOfficeSpecifications());
        com.setNumberOfCabins(dto.getNumberOfCabins());
        com.setNumberOfWashrooms(dto.getNumberOfWashrooms());
        com.setParkingAvailable(dto.getParkingAvailable());
        com.setFacilities(dto.getFacilities());
        com.setFloorPreference(dto.getFloorPreference());
        com.setLocatedOn(dto.getLocatedOn());
        com.setOfficeSpreadOver(dto.getOfficeSpreadOver());
        com.setAgeOfProperty(dto.getAgeOfProperty());

        return com;
    }





}
