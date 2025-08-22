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
@Table(name = "RentPGDetails")
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

    @ElementCollection
    @CollectionTable(name = "rent_pg_amenities", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "amenity")
    private List<String> amenities; // PG-specific amenities


    @OneToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private RentProperty property;


}
