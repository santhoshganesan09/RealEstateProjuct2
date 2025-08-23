package com.example.RealEstateProjuct.dto.RentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentFlatDetailsDTO {

    private Long id;
    private String availableFor;
    private String furnishingStatus;
    private Integer ageOfProperty;
    private Integer floorNo;
    private Integer totalFloors;
    private Integer balconies;
    private List<Map<String, Object>> amenities; // changed to accept id maps
}