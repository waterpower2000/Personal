package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.Mayuri_EV_Vehicle.model.Region;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
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
    private String customer_name; 
    private String customer_Number;
    private String adharCardNo;
    private String panCardNo;
    private String gstNo;
    private String customer_Address;
    private String gurranterName;
    private String gurranterPhoneNumber;
    private String gurranterAddress;
    private String registrationCharge;
    private String insuranceCharge;
    private String hsrfCharge;
    private String hypoCharge;
    private String licenseCharge;
    private Double perQuantityPrice;
    private String discount;
    private Double totalPrice;
    private String cashAmount;
    private String paymentMode;
    private String checkNumber;
    private String bankName;
    private String checkAmount;
    private String online_tran_type;
    private String online_tran;
    private String onlineAmount;
    private String guarantorAmount;
    private String region;
    private String purchaseDate;
    private String createdBy;
    private double sales_gst;
    private String sales_tranaction_date;
}
