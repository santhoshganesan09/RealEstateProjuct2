package com.example.RealEstateProjuct.dto.RentDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Map;
import java.util.Set;

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
    private Set<Map<String, Object>> amenities; // changed to accept id maps
}