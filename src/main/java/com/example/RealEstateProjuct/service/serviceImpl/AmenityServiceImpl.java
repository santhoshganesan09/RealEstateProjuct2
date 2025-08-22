package com.example.RealEstateProjuct.service.serviceImpl;

import com.example.RealEstateProjuct.model.Amenity;
import com.example.RealEstateProjuct.repo.AmenityRepo;
import com.example.RealEstateProjuct.service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepo amenityRepo;


    @Override
    public Amenity createAmenity(String name) {
        return amenityRepo.findByName(name)
                .orElseGet(() -> amenityRepo.save(Amenity.builder().name(name).build()));
    }

    @Override
    public List<Amenity> getAllAmenities() {
        return amenityRepo.findAll();
    }

    @Override
    public Amenity getAmenityById(Long id) {
        return amenityRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenity not found with id " + id));
    }

    @Override
    public void deleteAmenity(Long id) {
        amenityRepo.deleteById(id);
    }
}
