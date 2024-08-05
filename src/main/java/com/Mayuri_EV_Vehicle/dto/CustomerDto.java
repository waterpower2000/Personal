package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private String id;
    private String customer_name;
    private String customer_number;
    private String customer_AadhaarCard;
    private String customer_PanCard;
    private String customer_GSTNumber;
    private String customer_address;
}
