package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.Mayuri_EV_Vehicle.model.Region;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
	private String id;
    private String orderId;
    private String invoiceNumber;
    private String purchaseType;
    private String eriksha_variant_details_id;
    private String variantName;
    private String variantColour;
    private String sparePartsName;
    private String companyName;
    private String vehicleType;
    private String chassis_number;
    private String model_number;
    private String engine_number;
    private String motor_number;
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
    private String region;
    private String purchaseDate;
    private String createdBy;
    private double purchase_gst;
    private String purchase_tranaction_date;
}
