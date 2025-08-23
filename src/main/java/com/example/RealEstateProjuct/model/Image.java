package com.example.RealEstateProjuct.model;

import com.example.RealEstateProjuct.model.RentProperty.RentProperty;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url; // Path or URL of the image

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = true)
    private Property property;

    // For Rent Property
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_property_id" , nullable = true)
    private RentProperty rentProperty;

}
