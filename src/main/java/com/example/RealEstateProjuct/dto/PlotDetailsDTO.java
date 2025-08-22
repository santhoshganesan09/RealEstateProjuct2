package com.example.RealEstateProjuct.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlotDetailsDTO {

    private Long id;
    private String plotSize;
    private String landType;
    private String zoningType;
    private String facing;
    private String roadWidth;
    private Boolean boundaryWall;
    private Long propertyId; //<- property Table
}
