package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.ERikshaVariants;
import com.Mayuri_EV_Vehicle.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateERikshaDto {
    private String vehicle_id;
    private VehicleDto vehicle;
    private String name;
    private String companyName;
    private Type type;
    private String region;
}
