package com.example.RealEstateProjuct.model;

import com.example.RealEstateProjuct.enumClass.CurrencyType;
import com.example.RealEstateProjuct.enumClass.PriceType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {

    private Double amount;

    @Enumerated(EnumType.STRING)
    private CurrencyType currency; // INR, USD, etc.

    @Enumerated(EnumType.STRING)
    private PriceType type; // SALE, RENT
}
