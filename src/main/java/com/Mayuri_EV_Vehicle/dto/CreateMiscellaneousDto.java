package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMiscellaneousDto {
	
	private String money_type;
	private String expense_type;
	private String date;
	private String expenseType;
	private String amount;
	private String others_specification;
	private String miscellanous_heads_id;
}
