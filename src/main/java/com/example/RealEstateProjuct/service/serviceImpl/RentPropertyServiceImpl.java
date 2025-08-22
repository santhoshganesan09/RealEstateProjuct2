package com.example.RealEstateProjuct.service.serviceImpl;

import com.example.RealEstateProjuct.dto.PropertyDTO;
import com.example.RealEstateProjuct.enumClass.PropertyType;
import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.example.RealEstateProjuct.repo.PropertyRepo;
import com.example.RealEstateProjuct.service.RentPropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class RentPropertyServiceImpl implements RentPropertyService {

    private final PropertyRepo propertyRepo;
    private final PropertyServiceImpl basePropertyServiceImpl;

    @Override
    public PropertyDTO createRentProperty(PropertyDTO dto) {
        dto.setPropertyType(PropertyType.RENT.name());
        return basePropertyServiceImpl.createProperty(dto);
    }

    @Override
    public PropertyDTO getRentPropertyById(Long id) {
        PropertyDTO dto = basePropertyServiceImpl.getPropertyById(id);
        if (!PropertyType.RENT.name().equalsIgnoreCase(dto.getPropertyType())) {
            throw new RuntimeException("This property is not a RENT property");
        }
        return dto;
    }

    @Override
    public List<PropertyDTO> getAllRentProperties() {
        return basePropertyServiceImpl.getAllProperties().stream()
                .filter(p -> PropertyType.RENT.name().equalsIgnoreCase(p.getPropertyType()))
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDTO updateRentProperty(Long id, PropertyDTO dto) {
        dto.setPropertyType(PropertyType.RENT.name());
        return basePropertyServiceImpl.updateProperty(id, dto);
    }

    @Override
    public void deleteRentProperty(Long id) {
        PropertyDTO dto = getRentPropertyById(id);
        if (!PropertyType.RENT.name().equalsIgnoreCase(dto.getPropertyType())) {
            throw new RuntimeException("Cannot delete non-RENT property");
        }
        basePropertyServiceImpl.deleteProperty(id);
    }

    @Override
    public List<PropertyDTO> filterRentProperties(String category,
                                                  Double minPrice,
                                                  Double maxPrice,
                                                  String amenity,
                                                  String createdByRole,
                                                  String subtype,
                                                  Boolean verified,
                                                  String location,
                                                  String city) {
        return basePropertyServiceImpl.filterProperties(category, minPrice, maxPrice,
                        amenity, createdByRole, subtype, verified, location, city)
                .stream()
                .filter(p -> PropertyType.RENT.name().equalsIgnoreCase(p.getPropertyType()))
                .collect(Collectors.toList());
    }



}
