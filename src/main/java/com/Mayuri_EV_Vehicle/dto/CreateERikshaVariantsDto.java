package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.EAuto;
import com.Mayuri_EV_Vehicle.entity.ERiksha;
import com.Mayuri_EV_Vehicle.entity.ERikshaVariantDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateERikshaVariantsDto {
    private String variantName;
    private String vehicleId;
    private String Color;
    private int price;
    private String features;
}
