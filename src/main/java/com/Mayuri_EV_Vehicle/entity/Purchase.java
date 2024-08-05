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
@Table(name="TB_PURCHASE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id 
    @GenericGenerator(name = "Application-Generic-Generator", strategy = "com.Mayuri_EV_Vehicle.config.ApplicationIdentityGenerator")
//  @SequenceGenerator(initialValue = 112233445,allocationSize = 1,sequenceName = "custid",name="custid")
    @GeneratedValue(generator = "Application-Generic-Generator")
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

    @Column(name = "manufacturer_number")
    private String manufacturerNumber;

    @Column(name = "manufacturer_name")
    private String manufacturerName;

    @Lob
    @Column(name = "manufacturer_address")
    private String manufacturerAddress;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_number")
    private String supplierNumber;

    @Column(name = "adhar_no")
    private String adharCardNo;

    @Column(name = "pan_no")
    private String panCardNo;

    @Column(name = "gst_no")
    private String gstNo;

    @Lob
    @Column(name = "supplier_address")
    private String supplierAddress;

    @Column(name = "per_quantity_price")
    private Double perQuantityPrice;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "created_on")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime purchaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name="region", nullable = false)
    private Region region;

    @Column(name="created_by", nullable = false)
    private String createdBy;
    
    @Column(name="region_name", nullable = false)
    private String region_name;

    @Column(name="gst_price")
    private Double gst;
    
    @Column(name="full_payment")
    private boolean full_payment;

    @Column(name="purchase_tranaction_date")
    private String purchase_tranaction_date;

    public Purchase(String orderId, String invoiceNumber, TypeList type, String eAutoVariantDetails, String sparePartVariantDetailsId ,String manufacturerNumber,
                    String manufacturerName, String manufacturerAddress, String supplierName,
                    String supplierNumber, String adharCardNo, String panCardNo,String gstNo, String supplierAddress,
                    Double perQuantityPrice, Double totalPrice, Region region, String createdBy, String region_name, Double gst,Boolean full_payment,String purchase_tranaction_date){

        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.type = type;
        this.eAutoVariantDetails = eAutoVariantDetails;
        this.sparePartVariantDetailsId = sparePartVariantDetailsId;
        this.manufacturerNumber = manufacturerNumber;
        this.manufacturerName = manufacturerName;
        this.manufacturerAddress = manufacturerAddress;
        this.supplierName = supplierName;
        this.supplierNumber = supplierNumber;
        this.adharCardNo = adharCardNo;
        this.panCardNo = panCardNo;
        this.gstNo = gstNo;
        this.supplierAddress = supplierAddress;
        this.perQuantityPrice = perQuantityPrice;
        this.totalPrice = totalPrice;
        this.region = region;
        this.createdBy = createdBy;
        this.purchaseDate = LocalDateTime.now();
        this.region_name = region_name;
        this.gst=gst;
        this.full_payment = full_payment;
        this.purchase_tranaction_date=purchase_tranaction_date;
    }



}
