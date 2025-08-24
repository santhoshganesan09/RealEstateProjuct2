package com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails;

import com.example.RealEstateProjuct.model.Amenity;
import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rent_pg_details")
public class RentPGDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String forBoysOrGirls; // boys / girls
    private String availableFor; // students / working professionals
    private String sharingType; // single / double / triple
    private String pgType; // PG building / floor
    private String pgServices; // food, laundry, cleaning, etc.
    private String furnishingStatus;
    private Integer totalCapacity;
    private Integer bedrooms;


    @OneToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    @JsonBackReference
    private RentProperty property;


    @ManyToMany
    @JoinTable(
            name = "rent_pg_amenities",
            joinColumns = @JoinColumn(name = "pg_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

}
