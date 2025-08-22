package com.example.RealEstateProjuct.model.RentProperty;


import com.example.RealEstateProjuct.enumClass.RentProperty.RentPropertyType;
import com.example.RealEstateProjuct.model.*;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentCommercialDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentFlatDetails;
import com.example.RealEstateProjuct.model.RentProperty.RentPropertyDetails.RentPGDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private RentFlatDetails flatDetails;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private RentPGDetails pgDetails;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private RentCommercialDetails commercialDetails;

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
