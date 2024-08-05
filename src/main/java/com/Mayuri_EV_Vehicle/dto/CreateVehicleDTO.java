package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVehicleDTO {
    private String name;
    private String companyName;

    private String type;
    private String vehicleType;
    private String variantName;
    private String vehicleId;
    private String Color;
    private int price;
    private String features;
}
