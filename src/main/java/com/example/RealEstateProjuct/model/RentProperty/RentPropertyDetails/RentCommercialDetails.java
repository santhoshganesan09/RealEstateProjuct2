package com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails;

import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentCommercialDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer seatCount;
    private String officeSpecifications;
    private Integer numberOfCabins;
    private Integer numberOfWashrooms;
    private Boolean parkingAvailable;
    private String facilities; // Reception Area, Centralized AC, UPS, Fire Safety, etc.
    private String floorPreference; // basement, ground floor, terrace
    private String locatedOn; // basement, ground floor, terrace
    private String officeSpreadOver;
    private Integer ageOfProperty;

    @OneToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    @JsonIgnoreProperties("commercialDetails") // optional
    private RentProperty property;

}
