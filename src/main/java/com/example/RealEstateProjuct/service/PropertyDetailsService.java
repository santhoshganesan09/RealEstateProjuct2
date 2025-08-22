package com.example.RealEstateProjuct.service;

import com.example.RealEstateProjuct.dto.CommercialOfficeDetailsDTO;
import com.example.RealEstateProjuct.dto.FlatDetailsDTO;
import com.example.RealEstateProjuct.dto.HouseDetailsDTO;
import com.example.RealEstateProjuct.dto.PlotDetailsDTO;

public interface PropertyDetailsService {

    HouseDetailsDTO getHouseDetails(Long id);
    FlatDetailsDTO getFlatDetails(Long id);
    PlotDetailsDTO getPlotDetails(Long id);
    CommercialOfficeDetailsDTO getCommercialDetails(Long id);

    HouseDetailsDTO saveHouseDetails(HouseDetailsDTO dto);
    FlatDetailsDTO saveFlatDetails(FlatDetailsDTO dto);
    PlotDetailsDTO savePlotDetails(PlotDetailsDTO dto);
    CommercialOfficeDetailsDTO saveCommercialDetails(CommercialOfficeDetailsDTO dto);

    void deleteHouseDetails(Long id);
    void deleteFlatDetails(Long id);
    void deletePlotDetails(Long id);
    void deleteCommercialDetails(Long id);

}
