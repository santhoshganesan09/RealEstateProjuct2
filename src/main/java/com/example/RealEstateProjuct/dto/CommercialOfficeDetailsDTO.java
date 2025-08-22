package com.example.RealEstateProjuct.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommercialOfficeDetailsDTO {

    private Long id;
    private String officeType;
    private Double carpetArea;
    private String furnishingStatus;
    private Integer parkingSpaces;
    private Integer conferenceRooms;
    private Integer workstations;
    private boolean receptionArea;
    private Integer floorNumber;
    private Integer totalFloor;
    private boolean powerBackup;
    private boolean cafeteria;
}
