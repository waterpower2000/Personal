package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.EAuto;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EAutoVariantsDto {
    private String id;
    private String variantName;
    private String Color;
    private int price;
    private String features;
}
