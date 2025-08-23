package com.example.RealEstateProjuct.model.RentProperty;

import com.example.RealEstateProjuct.enumClass.RentProperty.RentPeriod;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@Setter
@Getter
public class RentPrice {

    private Double amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private RentPeriod period;


}
