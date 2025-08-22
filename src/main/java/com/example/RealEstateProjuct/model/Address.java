package com.example.RealEstateProjuct.model;


import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    private String houseNo;
    private String street;
    private String locality;
    private String city;
    private String state;
    private String country;
    private String pincode;
}
