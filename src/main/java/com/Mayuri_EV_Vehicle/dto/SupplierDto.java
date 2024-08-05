package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
    private String id;
    private String supplier_name;
    private String supplier_number;
    private String supplier_AadhaarCard;
    private String supplier_PanCard;
    private String supplier_GSTNumber;
    private String supplier_address;
}
