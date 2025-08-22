package com.example.RealEstateProjuct.model.PropertyDetails;

import com.example.RealEstateProjuct.model.Property;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "house_details")
public class HouseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer bedrooms;
    private Integer bathrooms;
    private Integer totalFloors;
    private String furnishingStatus;
    private String carParking;
    private Double plotArea; // in sq.ft or sq.m
    private Boolean privateGarden;
    private Boolean parkingAvailable;

    @OneToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property;


}
