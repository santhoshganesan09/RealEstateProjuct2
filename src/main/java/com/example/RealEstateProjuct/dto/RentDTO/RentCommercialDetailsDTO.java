package com.example.RealEstateProjuct.dto.RentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentCommercialDetailsDTO {

    private Long id;
    private Integer seatCount;
    private String officeSpecifications;
    private Integer numberOfCabins;
    private Integer numberOfWashrooms;
    private Boolean parkingAvailable;
    private String facilities;
    private String floorPreference;
    private String locatedOn;
    private String officeSpreadOver;
    private Integer ageOfProperty;
}
