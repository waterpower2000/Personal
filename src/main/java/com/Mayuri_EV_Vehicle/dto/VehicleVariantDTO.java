package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleVariantDTO {
    private String vehicleId;
    private String name;
    private String companyName;
    private Integer quantity;
    private String type;
    private String region;
    private String variantId;
    private String variantName;
    private String Color;
    private int price;
    private String features;
    private String createdBy;
    private LocalDateTime createdOn;
    private String updatedBy;
    private LocalDateTime updatedOn;
}
