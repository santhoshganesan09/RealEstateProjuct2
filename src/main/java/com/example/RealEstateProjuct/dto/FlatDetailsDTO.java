package com.example.RealEstateProjuct.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlatDetailsDTO {

    private Long id;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer floorNo;
    private Integer totalFloors;
    private String furnishingStatus;
    private Integer balconies;
}
