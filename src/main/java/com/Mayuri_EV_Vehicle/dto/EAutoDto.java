package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EAutoDto {

    private String id;
    private int quantity;
    private VehicleDto vehicle;
    private String vehicle_id;
    private String name;
    private String companyName;
    private String region;

}
