package com.Mayuri_EV_Vehicle.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiscellaneousDto {
	
	private String id;
    private String money_type;
    private String date;
    private String expenseTypeList;
    private String amount;
    private String specification;
    private String region;
    private LocalDate tran_date;
    private String account_name;
}
