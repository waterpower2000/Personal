package com.Mayuri_EV_Vehicle.dto;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private String id;
    private String billNumber;
    private double amount;
    private Date billDate;
}
