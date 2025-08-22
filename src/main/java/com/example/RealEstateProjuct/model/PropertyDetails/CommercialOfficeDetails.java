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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommercialOfficeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String officeType;

    private Double carpetArea;
    private String furnishingStatus;
    private Integer parkingSpaces;
    private Integer conferenceRooms;
    private Integer workstations;
    private boolean receptionArea;
    private Integer floorNumber;
    private Integer totalFloor;
    private boolean powerBackup;
    private boolean cafeteria;

    @OneToOne
    @JoinColumn(name = "property_id", nullable = false)
    @JsonIgnore
    private Property property;
}
