package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEAutoDto {
    private String vehicle_id;
    private VehicleDto vehicle;
    private String name;
    private String companyName;
    private Type type;
    private EAutoVariants eAutoVariants;
    private String region;
}
