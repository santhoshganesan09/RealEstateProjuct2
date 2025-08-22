package com.example.RealEstateProjuct.model.RentProperty;

import com.example.RealEstateProjuct.enumClass.RentProperty.RentPeriod;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class RentPrice {

    private Double amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private RentPeriod period;


}
