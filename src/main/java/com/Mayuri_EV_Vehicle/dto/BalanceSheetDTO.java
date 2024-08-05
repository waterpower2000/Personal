package com.Mayuri_EV_Vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceSheetDTO {
	
	private double purchase_amount;
	private double sales_amount;
	private double miscellaneous_income;
	private double miscellaneous_expense;
	private double miscellaneous_hsrp;
	private double miscellaneous_insurance;
	private double miscellaneous_hypo;
	private double miscellaneous_driving;
	private double miscellaneous_registration;
	private double opening_stock;
	private double closing_stock;

	private double miscellaneous_rent;
	private double miscellaneous_electricity;
	private double miscellaneous_salary;
	private double miscellaneous_travelling;
	private double miscellaneous_food;
	private double miscellaneous_incentive;
	private double miscellaneous_commission;
	private double miscellaneous_office;
	private double miscellaneous_broadband;
	private double miscellaneous_others;
	private double miscellaneous_credit_commission;
	private double pl_gross_profit_loss;

	private double stock;
	private double profit;
	private double miscellaneous_petty;
	private double sales_gross_gst;
	private double purchase_gross_gst;
	HashMap<String,Double> trading_debit_map;
	HashMap<String,Double> profitloss_debit_map;
	HashMap<String,Double> balancesheet_debit_map;
	HashMap<String,Double> trading_credit_map;
	HashMap<String,Double> profitloss_credit_map;
	HashMap<String,Double> balancesheet_credit_map;
	private double total_liablities;
	private double total_assets;

}
