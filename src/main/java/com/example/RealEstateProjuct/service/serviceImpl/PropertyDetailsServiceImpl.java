package com.example.RealEstateProjuct.service.serviceImpl;

import com.example.RealEstateProjuct.dto.CommercialOfficeDetailsDTO;
import com.example.RealEstateProjuct.dto.FlatDetailsDTO;
import com.example.RealEstateProjuct.dto.HouseDetailsDTO;
import com.example.RealEstateProjuct.dto.PlotDetailsDTO;
import com.example.RealEstateProjuct.mapper.PropertyDetailsMapper;
import com.example.RealEstateProjuct.model.Property;
import com.example.RealEstateProjuct.model.PropertyDetails.CommercialOfficeDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.FlatDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.HouseDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.PlotDetails;
import com.example.RealEstateProjuct.repo.*;
import com.example.RealEstateProjuct.service.PropertyDetailsService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropertyDetailsServiceImpl implements PropertyDetailsService {


    private final HouseDetailsRepo houseRepo;

    private final FlatDetailsRepo flatRepo;

    private final CommercialOfficeDetailsRepo commercialRepo;

    private final PlotDetailsRepo plotRepo;

    private final PropertyDetailsMapper mapper;

    private final PropertyRepo propertyRepo;



    @Override
    public HouseDetailsDTO getHouseDetails(Long id) {
        //return mapper.toHouseDTO(houseRepo.findById(id).orElse(null));
        return houseRepo.findById(id)
                .map(mapper::toHouseDTO)
                .orElse(null);
    }

    @Override
    public FlatDetailsDTO getFlatDetails(Long id) {
        //return mapper.toFlatDTO(flatRepo.findById(id).orElse(null));
        return flatRepo.findById(id)
                .map(mapper::toFlatDTO)
                .orElse(null);
    }

    @Override
    public PlotDetailsDTO getPlotDetails(Long id) {
       // return mapper.toPlotDTO(plotRepo.findById(id).orElse(null));
        return plotRepo.findById(id)
                .map(mapper::toPlotDTO)
                .orElse(null);
    }

    @Override
    public CommercialOfficeDetailsDTO getCommercialDetails(Long id) {
        //return mapper.toCommercialDTO(commercialRepo.findById(id).orElse(null));
        return commercialRepo.findById(id)
                .map(mapper::toCommercialDTO)
                .orElse(null);
    }

    @Override
    public HouseDetailsDTO saveHouseDetails(HouseDetailsDTO dto) {
        HouseDetails house = HouseDetails.builder()
                .bedrooms(dto.getBedrooms())
                .furnishingStatus(dto.getFurnishingStatus())
                .carParking(dto.getCarParking())
                .bathrooms(dto.getBathrooms())
                .totalFloors(dto.getTotalFloors())
                .plotArea(dto.getPlotArea())
                .privateGarden(dto.getPrivateGarden())
                .parkingAvailable(dto.getParkingAvailable())
                .build();
        return mapper.toHouseDTO(houseRepo.save(house));
    }

    @Override
    public FlatDetailsDTO saveFlatDetails(FlatDetailsDTO dto) {
        FlatDetails flat = FlatDetails.builder()
                .bedrooms(dto.getBedrooms())
                .bathrooms(dto.getBathrooms())
                .floorNo(dto.getFloorNo())
                .totalFloors(dto.getTotalFloors())
                .furnishingStatus(dto.getFurnishingStatus())
                .balconies(dto.getBalconies())
                .build();
        return mapper.toFlatDTO(flatRepo.save(flat));
    }

    @Override
    public PlotDetailsDTO savePlotDetails(PlotDetailsDTO dto) {
        PlotDetails plot = PlotDetails.builder()
                .plotSize(dto.getPlotSize())
                .landType(dto.getLandType())
                .zoningType(dto.getZoningType())
                .facing(dto.getFacing())
                .roadWidth(dto.getRoadWidth())
                .boundaryWall(dto.getBoundaryWall())
                .build();

        // Set property reference
        if(dto.getPropertyId() != null) {
            Property property = propertyRepo.findById(dto.getPropertyId())
                    .orElseThrow(() -> new RuntimeException("Property not found"));
            plot.setProperty(property);
        }


        return mapper.toPlotDTO(plotRepo.save(plot));
    }

    @Override
    public CommercialOfficeDetailsDTO saveCommercialDetails(CommercialOfficeDetailsDTO dto) {
        CommercialOfficeDetails office = CommercialOfficeDetails.builder()
                .officeType(dto.getOfficeType())
                .carpetArea(dto.getCarpetArea())
                .furnishingStatus(dto.getFurnishingStatus())
                .parkingSpaces(dto.getParkingSpaces())
                .conferenceRooms(dto.getConferenceRooms())
                .workstations(dto.getWorkstations())
                .receptionArea(dto.isReceptionArea())
                .floorNumber(dto.getFloorNumber())
                .totalFloor(dto.getTotalFloor())
                .powerBackup(dto.isPowerBackup())
                .cafeteria(dto.isCafeteria())
                .build();
        return mapper.toCommercialDTO(commercialRepo.save(office));
    }

    @Override
    public void deleteHouseDetails(Long id) {
        houseRepo.deleteById(id);
    }

    @Override
    public void deleteFlatDetails(Long id) {
        flatRepo.deleteById(id);
    }

    @Override
    public void deletePlotDetails(Long id) {
        plotRepo.deleteById(id);
    }

    @Override
    public void deleteCommercialDetails(Long id) {
        commercialRepo.deleteById(id);
    }






}
