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
public class RentPGDetailsDTO {

    private Long id;
    private String forBoysOrGirls;
    private String availableFor;
    private String sharingType;
    private String pgType;
    private String pgServices;
    private String furnishingStatus;
    private Integer totalCapacity;
    private Integer bedrooms;
    private List<Map<String, Object>> amenities; // changed to accept id maps
}
