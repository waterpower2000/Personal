package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EAutoVariantDetailsDto {
    private String id;
    private String variantName;
    private String colour;
    private String type;
    private String price;
    private String chassis_number;
    private String model_number;
    private String engine_number;
    private String region;
}
