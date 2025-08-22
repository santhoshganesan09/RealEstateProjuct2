package com.example.RealEstateProjuct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseDetailsDTO {

    private Long id;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer totalFloors;
    private Double plotArea;
    private String furnishingStatus;
    private String carParking;
    private Boolean privateGarden;
    private Boolean parkingAvailable;
}
