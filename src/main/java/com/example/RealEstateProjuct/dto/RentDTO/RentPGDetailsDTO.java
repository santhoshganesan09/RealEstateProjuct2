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
    private Set<Map<String, Object>> amenities; // changed to accept id maps
}
