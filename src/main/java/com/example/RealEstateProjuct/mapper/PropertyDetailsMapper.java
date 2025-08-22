package com.example.RealEstateProjuct.mapper;

import com.example.RealEstateProjuct.dto.CommercialOfficeDetailsDTO;
import com.example.RealEstateProjuct.dto.FlatDetailsDTO;
import com.example.RealEstateProjuct.dto.HouseDetailsDTO;
import com.example.RealEstateProjuct.dto.PlotDetailsDTO;
import com.example.RealEstateProjuct.model.PropertyDetails.CommercialOfficeDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.FlatDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.HouseDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.PlotDetails;
import org.springframework.stereotype.Component;

@Component
public class PropertyDetailsMapper {

    public HouseDetailsDTO toHouseDTO(HouseDetails house) {
        if (house == null) return null;
        return HouseDetailsDTO.builder()
                .id(house.getId())
                .bedrooms(house.getBedrooms())
                .bathrooms(house.getBathrooms())
                .totalFloors(house.getTotalFloors())
                .furnishingStatus(house.getFurnishingStatus())
                .carParking(house.getCarParking())
                .plotArea(house.getPlotArea())
                .privateGarden(house.getPrivateGarden())
                .parkingAvailable(house.getParkingAvailable())
                .build();
    }

    public FlatDetailsDTO toFlatDTO(FlatDetails flat) {
        if (flat == null) return null;
        return FlatDetailsDTO.builder()
                .id(flat.getId())
                .bedrooms(flat.getBedrooms())
                .bathrooms(flat.getBathrooms())
                .floorNo(flat.getFloorNo())
                .totalFloors(flat.getTotalFloors())
                .furnishingStatus(flat.getFurnishingStatus())
                .balconies(flat.getBalconies())
                .build();
    }

    public PlotDetailsDTO toPlotDTO(PlotDetails plot) {
        if (plot == null) return null;
        return PlotDetailsDTO.builder()
                .id(plot.getId())
                .plotSize(plot.getPlotSize())
                .landType(plot.getLandType())
                .zoningType(plot.getZoningType())
                .facing(plot.getFacing())
                .roadWidth(plot.getRoadWidth())
                .boundaryWall(plot.getBoundaryWall())
                .build();
    }

    public CommercialOfficeDetailsDTO toCommercialDTO(CommercialOfficeDetails office) {
        if (office == null) return null;
        return CommercialOfficeDetailsDTO.builder()
                .id(office.getId())
                .officeType(office.getOfficeType())
                .carpetArea(office.getCarpetArea())
                .furnishingStatus(office.getFurnishingStatus())
                .parkingSpaces(office.getParkingSpaces())
                .conferenceRooms(office.getConferenceRooms())
                .workstations(office.getWorkstations())
                .receptionArea(office.isReceptionArea())
                .floorNumber(office.getFloorNumber())
                .totalFloor(office.getTotalFloor())
                .powerBackup(office.isPowerBackup())
                .cafeteria(office.isCafeteria())
                .build();
    }

}
