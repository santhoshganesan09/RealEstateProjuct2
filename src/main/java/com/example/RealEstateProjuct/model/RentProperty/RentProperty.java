package com.example.RealEstateProjuct.model.RentProperty;


import com.example.RealEstateProjuct.enumClass.RentProperty.RentPropertyType;
import com.example.RealEstateProjuct.model.*;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentCommercialDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentPGDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    // Rent property type (FLAT / PG / COMMERCIAL)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RentPropertyType propertyType;

    // Category (same as Property: FLAT / PG / COMMERCIAL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Embedded
    private RentPrice rentPrice;

    @Embedded
    private Area area;


    private Integer bedrooms;
    private Integer bathrooms;

    @Embedded
    private Address address;

    private boolean isVerified;

    @Column(length = 500)
    private String featuresSummary;

    @OneToMany(mappedBy = "rentProperty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "rent_property_amenities",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "rent_property_tags", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    // --- Type-specific details ---
    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private RentFlatDetails flatDetails;

    public void setFlatDetails(RentFlatDetails flatDetails) {
        this.flatDetails = flatDetails;
        if (flatDetails != null) {
            flatDetails.setProperty(this); // Lombok-generated setter
        }
    }

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private RentPGDetails pgDetails;

    public void setPgDetails(RentPGDetails pgDetails) {
        this.pgDetails = pgDetails;
        if (pgDetails != null) {
            pgDetails.setProperty(this); // ✅ use 'setProperty', Lombok will generate it
        }
    }


    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnoreProperties("property") // avoids infinite recursion
    private RentCommercialDetails commercialDetails;

    public void setCommercialDetails(RentCommercialDetails commercialDetails) {
        this.commercialDetails = commercialDetails;
        if (commercialDetails != null) {
            commercialDetails.setProperty(this); // ensures bi-directional consistency
        }
    }

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }




}
