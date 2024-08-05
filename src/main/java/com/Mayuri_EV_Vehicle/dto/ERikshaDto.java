package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.ERikshaVariants;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERikshaDto {

    private String id;
    private String name;
    private VehicleDto vehicle;
    private String vehicle_id;
    private int quantity;
    private String companyName;
    private String region;

}
