package com.Mayuri_EV_Vehicle.dto;

import com.Mayuri_EV_Vehicle.entity.LocalDateTimeAttributeConverter;
import lombok.*;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.Mayuri_EV_Vehicle.model.Region;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSalesDto {
	private String eriksha_variant_details_id;
    private String type;
    private String sales_customer_name;
    private String sales_customer_number;
    private String sales_customer_AadhaarCard;
    private String sales_customer_PanCard;
    private String sales_customer_GSTNumber;
    private String sales_customer_address;
    private String battery_1;
    private String battery_2;
    private String battery_3;
    private String battery_4;
    private String battery_5;
    private String battery_start;
    private String battery_end;
    private String gurranterName;
    private String gurranterAddress;
    private String gurranterPhoneNumber;
    private String discount;
    private Double salesPrice;
    private Double sales_totalPrice;
    private String cashAmount;
    private String paymentMode;
    private String checkNumber;
    private String bankName;
    private String checkAmount;
    private String online_tran_type;
    private String online_tran;
    private String onlineAmount;
    private String guarantorAmount;
    private String registrationCharge;
    private String insuranceCharge;
    private String hsrfCharge;
    private String hypoCharge;
    private String licenseCharge;
    private Double sales_gst;
    private String sales_tranaction_date;
}
