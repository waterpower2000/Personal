package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateERikshaVariantDetailsDto {
    private String eriksha_variant_details_id;
    private String chassis_number;
    private String model_number;
    private String engine_number;
}
