package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEAutoVariantDetailsDto {
    private String eauto_variant_details_id;
    private String chassis_number;
    private String model_number;
    private String engine_number;
    private String battery_number;
    private String battery_maker_name;
    private String battery_warranty_start_date;
    private String battery_warranty_end_date;
    private String moter_number;
    private String controller_name;
    private String onRoadPrice;
}
