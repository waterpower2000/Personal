package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePartPaymentDto {
    private String id;
    private String sales_id;
    private String sales_customer_name;
    private String purchaseType;
    private String variantName;
    private String sparePartsName;
    private Double sales_totalPrice;
    private String cashAmount;
    private String checkNumber;
    private String checkAmount;
    private String online_tran_type;
    private String online_tran;
    private String onlineAmount;
    private String guarantorAmount;
    private String createdOn;
    private String tranaction_date;
}
