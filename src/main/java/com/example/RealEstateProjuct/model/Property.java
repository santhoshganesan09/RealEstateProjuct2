package com.example.RealEstateProjuct.model;

import com.example.RealEstateProjuct.enumClass.PropertyStatus;
import com.example.RealEstateProjuct.enumClass.PropertyType;
import com.example.RealEstateProjuct.model.PropertyDetails.CommercialOfficeDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.FlatDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.HouseDetails;
import com.example.RealEstateProjuct.model.PropertyDetails.PlotDetails;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import java.time.Instant;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PropertyType propertyType; // BUY / RENT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // FLAT / PLOT / VILLA etc.





    @Column(length = 50)
    private String subtype; // 3BHK, 2BHK, etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // Price Info
    @Embedded
    private Price price;

    // Area Info
    @Embedded
    private Area area;

    private Integer bedrooms;
    private Integer bathrooms;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PropertyStatus status; // ACTIVE / INACTIVE

    private Instant publishedAt;

    private boolean isVerified;

    @Column(length = 500)
    private String featuresSummary;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "property_amenities",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "property_tags", joinColumns = @JoinColumn(name = "property_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();  //sea view, pet-friendly, near metro, corner plot, luxury


    // --- One-to-One relationships for category-specific details ---
    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private FlatDetails flatDetails;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private PlotDetails plotDetails;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private CommercialOfficeDetails commercialOfficeDetails;

    @OneToOne(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference
    private HouseDetails houseDetails;



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
