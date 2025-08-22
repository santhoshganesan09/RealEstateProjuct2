package com.example.RealEstateProjuct.model;

import com.example.RealEstateProjuct.enumClass.AreaUnit;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area {

    private Double size;

    @Enumerated(EnumType.STRING)
    private AreaUnit unit; // sqft, sqmeter, etc.
}
