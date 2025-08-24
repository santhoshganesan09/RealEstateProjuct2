package com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails;


import com.example.RealEstateProjuct.model.Amenity;
import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;

import java.util.Set;

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


    @OneToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private RentProperty property;

    @ManyToMany
    @JoinTable(
            name = "rent_flat_amenities",
            joinColumns = @JoinColumn(name = "flat_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();


}
