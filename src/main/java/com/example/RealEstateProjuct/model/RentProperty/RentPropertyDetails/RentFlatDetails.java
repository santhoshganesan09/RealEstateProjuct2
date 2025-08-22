package com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails;


import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentFlatDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String availableFor; // family, single men, single women, company lease
    private String furnishingStatus;
    private Integer ageOfProperty;
    private Integer floorNo;
    private Integer totalFloors;
    private Integer balconies;

    @ElementCollection
    @CollectionTable(name = "rent_flat_amenities", joinColumns = @JoinColumn(name = "flat_id"))
    @Column(name = "amenity")
    private List<String> amenities;

    @OneToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private RentProperty property;



}
