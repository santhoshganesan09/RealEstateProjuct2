package com.example.RealEstateProjuct.controller;


import com.example.RealEstateProjuct.dto.CommercialOfficeDetailsDTO;
import com.example.RealEstateProjuct.dto.FlatDetailsDTO;
import com.example.RealEstateProjuct.dto.HouseDetailsDTO;
import com.example.RealEstateProjuct.dto.PlotDetailsDTO;
import com.example.RealEstateProjuct.service.PropertyDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/property-details")
@RequiredArgsConstructor
public class PropertyDetailsController {

    private final PropertyDetailsService propertyDetailsService;


    // -------------------- GET --------------------
    @GetMapping("/house/{id}")
    public ResponseEntity<HouseDetailsDTO> getHouse(@PathVariable Long id) {
        return ResponseEntity.ok(propertyDetailsService.getHouseDetails(id));
    }

    @GetMapping("/flat/{id}")
    public ResponseEntity<FlatDetailsDTO> getFlat(@PathVariable Long id) {
        return ResponseEntity.ok(propertyDetailsService.getFlatDetails(id));
    }

    @GetMapping("/plot/{id}")
    public ResponseEntity<PlotDetailsDTO> getPlot(@PathVariable Long id) {
        return ResponseEntity.ok(propertyDetailsService.getPlotDetails(id));
    }

    @GetMapping("/commercial/{id}")
    public ResponseEntity<CommercialOfficeDetailsDTO> getCommercial(@PathVariable Long id) {
        return ResponseEntity.ok(propertyDetailsService.getCommercialDetails(id));
    }

    // -------------------- CREATE / SAVE --------------------
    @PostMapping("/house")
    public ResponseEntity<HouseDetailsDTO> saveHouse(@RequestBody HouseDetailsDTO dto) {
        return ResponseEntity.ok(propertyDetailsService.saveHouseDetails(dto));
    }

    @PostMapping("/flat")
    public ResponseEntity<FlatDetailsDTO> saveFlat(@RequestBody FlatDetailsDTO dto) {
        return ResponseEntity.ok(propertyDetailsService.saveFlatDetails(dto));
    }

    @PostMapping("/plot")
    public ResponseEntity<PlotDetailsDTO> savePlot(@RequestBody PlotDetailsDTO dto) {
        return ResponseEntity.ok(propertyDetailsService.savePlotDetails(dto));
    }

    @PostMapping("/commercial")
    public ResponseEntity<CommercialOfficeDetailsDTO> saveCommercial(@RequestBody CommercialOfficeDetailsDTO dto) {
        return ResponseEntity.ok(propertyDetailsService.saveCommercialDetails(dto));
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/house/{id}")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        propertyDetailsService.deleteHouseDetails(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/flat/{id}")
    public ResponseEntity<Void> deleteFlat(@PathVariable Long id) {
        propertyDetailsService.deleteFlatDetails(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/plot/{id}")
    public ResponseEntity<Void> deletePlot(@PathVariable Long id) {
        propertyDetailsService.deletePlotDetails(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/commercial/{id}")
    public ResponseEntity<Void> deleteCommercial(@PathVariable Long id) {
        propertyDetailsService.deleteCommercialDetails(id);
        return ResponseEntity.noContent().build();
    }




}
