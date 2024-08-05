package com.Mayuri_EV_Vehicle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.Mayuri_EV_Vehicle.model.Region;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="TB_SALES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sales {
	
    @Id
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
//    @SequenceGenerator(initialValue= 445321686,allocationSize = 1,sequenceName = "sale_no",name="sale_no")
	@GeneratedValue(generator="Application-Generic-Generator")
    @Column(name = "e_auto_variant_details_id", nullable = false, unique = true)
    private String id;

    @Column(name = "order_id")
    private String orderId;
    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false)
    private TypeList type;


    @Column(name = "e_auto_variant_details")
    private String eAutoVariantDetails;

    @Column(name = "spare_part_id")
    private String sparePartVariantDetailsId;
    
    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "adhar_no")
    private String adharCardNo;

    @Column(name = "pan_no")
    private String panCardNo;

    @Column(name = "gst_no")
    private String gstNo;

    @Lob
    @Column(name = "customer_address")
    private String customerAddress;
    
    @Column(name = "battery_1")
    private String battery_1;
    
    @Column(name = "battery_2")
    private String battery_2;
    
    @Column(name = "battery_3")
    private String battery_3;
    
    @Column(name = "battery_4")
    private String battery_4;
    
    @Column(name = "battery_5")
    private String battery_5;
    
    @Column(name = "battery_start_date")
    private String battery_start;
    
    @Column(name = "battery_end_date")
    private String battery_end;

    @Column(name = "gurranter_name")
    private String gurranterName;

    @Lob
    @Column(name = "gurranter_address")
    private String gurranterAddress;

    @Column(name = "gurranter_phone")
    private String gurranterPhoneNumber;
    
    @Column(name = "reg_charge")
    private String regCharge;
    
    @Column(name = "ins_charge")
    private String insCharge;
    
    @Column(name = "hypo_charge")
    private String hypoCharge;
    
    @Column(name = "hsrf_charge")
    private String hsrfCharge;
    
    @Column(name = "license_charge")
    private String licenseCharge;

    @Column(name = "per_quantity_price")
    private Double perQuantityPrice;
    
    @Column(name = "discount")
    private String discount;

    @Column(name = "gst")
    private Double gst_sales;

    @Column(name = "total_price")
    private Double sales_totalPrice;

    @Column(name = "payment_mode")
    private String paymentMode;
    
    @Column(name = "cash_amount")
    private String cashAmount;

    @Column(name = "cheque_no")
    private String checkNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "check_amount")
    private String checkAmount;
    
    @Column(name= "online_type")
    private String online_tran_type;
    
    @Column(name= "online_transaction_id")
    private String online_tran;
    
    @Column(name= "online_amount")
    private String onlineAmount;
    
    @Column(name= "guarantor_amount")
    private String guarantorAmount;
    
    
    @Column(name = "created_on")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime saleDate;

    @Enumerated(EnumType.STRING)
    @Column(name="region", nullable = false)
    private Region region;

    @Column(name="created_by", nullable = false)
    private String createdBy;
    
    @Column(name="region_name")
    private String region_name;
    
    @Column(name="full_payment")
    private boolean full_payment;

    @Column(name="sales_tranaction_date")
    private String sales_tranaction_date;
    
	public Sales(String orderId, String invoiceNumber,TypeList type, String eAutoVariantDetails, String sparePartVariantDetailsId,String customerName,
            String customerNumber, String adharCardNo, String panCardNo,String gstNo, String customerAddress,
            String battery_1,String battery_2,String battery_3,String battery_4,String battery_5, String battery_start, String battery_end, 
            String gurranterName, String gurranterPhoneNumber, String gurranterAddress,
            String regCharge,String insCharge,String hypoCharge,String hsrfCharge,String licenseCharge,
            Double perQuantityPrice,String discount,Double gst_sales, Double sales_totalPrice,String paymentMode,String cashAmount, String checkNumber, String bankName, String checkAmount,
            String online_tran_type,String online_tran,String onlineAmount,String guarantorAmount, Region region, String createdBy, String region_name,Boolean full_payment,String sales_tranaction_date)
	{
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.type = type;
		this.eAutoVariantDetails = eAutoVariantDetails;
        this.sparePartVariantDetailsId = sparePartVariantDetailsId;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
        this.adharCardNo = adharCardNo;
        this.panCardNo = panCardNo;
        this.gstNo = gstNo;
        this.customerAddress = customerAddress;
        this.battery_1 = battery_1;
        this.battery_2 = battery_2;
        this.battery_3 = battery_3;
        this.battery_4 = battery_4;
        this.battery_5 = battery_5;
        this.battery_start = battery_start;
        this.battery_end = battery_end;
        this.gurranterName = gurranterName;
        this.gurranterAddress = gurranterAddress;
        this.gurranterPhoneNumber = gurranterPhoneNumber;
        this.regCharge = regCharge;
        this.insCharge = insCharge;
        this.hypoCharge = hypoCharge;
        this.hsrfCharge = hsrfCharge;
        this.licenseCharge = licenseCharge;
        this.perQuantityPrice = perQuantityPrice;
        this.discount = discount;
        this.gst_sales=gst_sales;
        this.sales_totalPrice = sales_totalPrice;
        this.paymentMode = paymentMode;
        this.cashAmount=cashAmount;
        this.checkNumber = checkNumber;
        this.bankName = bankName;
        this.checkAmount = checkAmount;
        this.online_tran_type= online_tran_type;
        this.online_tran=online_tran;
        this.onlineAmount=onlineAmount;
        this.guarantorAmount=guarantorAmount;
        this.region = region;
        this.createdBy = createdBy;
        this.saleDate = LocalDateTime.now();
        this.region_name = region_name;
        this.full_payment = full_payment;
        this.sales_tranaction_date=sales_tranaction_date;
	}
    
   
}
