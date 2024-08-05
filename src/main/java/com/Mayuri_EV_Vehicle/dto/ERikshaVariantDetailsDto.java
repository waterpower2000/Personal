package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.ERikshaVariants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ERikshaVariantDetailsDto {
    private String id;
    private ERikshaVariants eRikshaVariants;
    private String chassis_number;
    private String model_number;
    private String engine_number;
}
