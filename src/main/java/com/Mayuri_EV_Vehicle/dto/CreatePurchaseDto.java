package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.LocalDateTimeAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.Mayuri_EV_Vehicle.model.Region;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePurchaseDto {
    private String type;
    private String eriksha_variant_details_id;
    private String chassis_number;
    private String model_number;
    private String engine_number;
    private String battery_number;
    private String battery_maker_name;
    private String battery_warranty_start_date;
    private String battery_warranty_end_date;
    private String moter_number;
    private String controller_name;
    private String manufacturerNumber;
    private String manufacturerName;
    private String manufacturerAddress;
    private String supplierName;
    private String supplierNumber;
    private String adharCardNo;
    private String panCardNo;
    private String gstNo;
    private String supplierAddress;
    private Double perQuantityPrice;
    private Double totalPrice;
    private String onRoadPrice;
    private Double gst;
    private String purchase_tranaction_date;
}
