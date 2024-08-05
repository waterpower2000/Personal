package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePartPaymentDTO {
    private String id;
    private LocalDate createdOn;
    private Double perQuantityPrice;
    private String purchase_id;

}
