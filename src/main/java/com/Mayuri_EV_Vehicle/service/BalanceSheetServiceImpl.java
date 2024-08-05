package com.Mayuri_EV_Vehicle.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.BalanceSheetDTO;
import com.Mayuri_EV_Vehicle.dto.CreateBalanceDTO;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.MiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.PurchaseDto;
import com.Mayuri_EV_Vehicle.dto.SalesDto;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.ExpenseTypeList;
import com.Mayuri_EV_Vehicle.entity.Purchase;



@Service
@Transactional
public class BalanceSheetServiceImpl implements BalanceSheetService {
	
	private PurchaseService purchaseService;
	private SalesService salesService;
	private MiscellaneousService miscellaneousService;
	
	public BalanceSheetServiceImpl(PurchaseService purchaseService,SalesService salesService,MiscellaneousService miscellaneousService)
	{
		this.purchaseService = purchaseService;
		this.salesService = salesService;
		this.miscellaneousService = miscellaneousService;
	}

	@Override
	public List<PurchaseDto> getPurchase(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
		List<PurchaseDto> list1 = new ArrayList<>();
		for(PurchaseDto purchaseDto : allPurchase)
		{
			String str = purchaseDto.getPurchase_tranaction_date();
			String str1;
			str1= str.substring(6,10)+"-"+str.substring(3,5)+"-"+str.substring(0,2);
			purchaseDto.setPurchase_tranaction_date(str1);
			if(purchaseDto.getPurchase_tranaction_date().compareTo(createBalanceDTO.getDateFrom())>=0 && purchaseDto.getPurchase_tranaction_date().compareTo(createBalanceDTO.getDateTo())<=0)
			{
				String date =purchaseDto.getPurchaseDate();
				date = date.substring(0, Math.min(date.length(), 10));
				purchaseDto.setPurchaseDate(date);
				list1.add(purchaseDto);
			}
		}
		return list1;
	}
	
	@Override
	public List<PurchaseDto> getFilterPurchase(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
		List<PurchaseDto> list1 = new ArrayList<>();
		List<SalesDto> allSales = salesService.getAllSales(domainUser);
		int flag=0;
		for(PurchaseDto purchaseDto : allPurchase)
		{
			for (SalesDto sales : allSales) {
				if (purchaseDto.getChassis_number().equals(sales.getChassis_number()))
					flag = 1;
			}
			String str = purchaseDto.getPurchase_tranaction_date();
			String str1;
			str1= str.substring(6,10)+"-"+str.substring(3,5)+"-"+str.substring(0,2);
			purchaseDto.setPurchase_tranaction_date(str1);
			if(purchaseDto.getPurchase_tranaction_date().compareTo(createBalanceDTO.getDateFrom())>=0 && purchaseDto.getPurchase_tranaction_date().compareTo(createBalanceDTO.getDateTo())<=0 &&flag==0)
			{
				String date =purchaseDto.getPurchaseDate();
				date = date.substring(0, Math.min(date.length(), 10));
				purchaseDto.setPurchaseDate(date);
				list1.add(purchaseDto);
			}
			flag=0;
		}
		return list1;
	}

	@Override
	public List<PurchaseDto> openingStock(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<PurchaseDto>purchaseList=purchaseService.getAllPurchaseDetails(domainUser);
		List<SalesDto> allSales = salesService.getAllSales(domainUser);
		List<PurchaseDto> list8 = new ArrayList<>();
		int flag=0;
		for(PurchaseDto purchaseDto:purchaseList)
		{
			String str = purchaseDto.getPurchase_tranaction_date();
			String str1;
			str1= str.substring(6,10)+"-"+str.substring(3,5)+"-"+str.substring(0,2);
			purchaseDto.setPurchase_tranaction_date(str1);
			if(purchaseDto.getPurchase_tranaction_date().compareTo(createBalanceDTO.getDateFrom())<=0)
			{
				for(SalesDto sales: allSales) {
					String st = sales.getSales_tranaction_date();
					String st1;
					st1= st.substring(6,10)+"-"+st.substring(3,5)+"-"+st.substring(0,2);
					sales.setSales_tranaction_date(st1);
					if (purchaseDto.getChassis_number().equals(sales.getChassis_number()) && sales.getSales_tranaction_date().compareTo(createBalanceDTO.getDateFrom())<=0) {
						flag =1;
					}
				}
				if(flag==0)
				{
					list8.add(purchaseDto);
				}
			}
			flag=0;
		}
		return list8;
	}

	@Override
	public List<PurchaseDto> closingStock(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<PurchaseDto>purchaseList=purchaseService.getAllPurchaseDetails(domainUser);
		List<SalesDto> allSales = salesService.getAllSales(domainUser);
		List<PurchaseDto> list9 = new ArrayList<>();
		int flag=0;
		for(PurchaseDto purchaseDto:purchaseList)
		{
			for (SalesDto sales : allSales) {
				if (purchaseDto.getChassis_number().equals(sales.getChassis_number())) {
					flag = 1;
				}
			}
			if (flag == 0) {
				list9.add(purchaseDto);
			}
			flag=0;
		}
		return list9;
	}

	@Override
	public List<SalesDto> getSale(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<SalesDto> allSale = salesService.getAllSales(domainUser);
		List<SalesDto> list2 = new ArrayList<>();
		for(SalesDto salesDto : allSale)
		{
			String str = salesDto.getSales_tranaction_date();
			String str1;
			str1= str.substring(6,10)+"-"+str.substring(3,5)+"-"+str.substring(0,2);
			salesDto.setSales_tranaction_date(str1);
			if(salesDto.getSales_tranaction_date().compareTo(createBalanceDTO.getDateFrom())>=0 && salesDto.getSales_tranaction_date().compareTo(createBalanceDTO.getDateTo())<=0)
			{
				String date =salesDto.getPurchaseDate();
				date = date.substring(0, Math.min(date.length(), 10));
				salesDto.setPurchaseDate(date);

				list2.add(salesDto);
			}
		}
		return list2;
	}
	
	@Override
	public List<MiscellaneousDto> getRTOMisc(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<MiscellaneousDto> allMisc = miscellaneousService.getRTOMiscDebit(domainUser);
		List<MiscellaneousDto> list3 = new ArrayList<>();
		for(MiscellaneousDto miscellaneousDto : allMisc)
		{
			String str = miscellaneousDto.getDate();
			String str1;
			str1= str.substring(6,10)+"-"+str.substring(3,5)+"-"+str.substring(0,2);
			miscellaneousDto.setDate(str1);
			System.out.println(miscellaneousDto.getDate());
			if(miscellaneousDto.getDate().compareTo(createBalanceDTO.getDateFrom())>=0 && miscellaneousDto.getDate().compareTo(createBalanceDTO.getDateTo())<=0)
			{
				String date =miscellaneousDto.getDate();
				date = date.substring(0, Math.min(date.length(), 10));
				miscellaneousDto.setDate(date);
				list3.add(miscellaneousDto);
			}
		}
		return list3;
	}
	
	@Override
	public List<MiscellaneousDto> getRestMisc(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<MiscellaneousDto> allMisc = miscellaneousService.getRestMiscDebit(domainUser);
		List<MiscellaneousDto> list3 = new ArrayList<>();
		for(MiscellaneousDto miscellaneousDto : allMisc)
		{
			String str = miscellaneousDto.getDate();
			String str1;
			str1= str.substring(6,10)+"-"+str.substring(3,5)+"-"+str.substring(0,2);;
			miscellaneousDto.setDate(str1);
			System.out.println(miscellaneousDto.getDate());
			if(miscellaneousDto.getDate().compareTo(createBalanceDTO.getDateFrom())>=0 && miscellaneousDto.getDate().compareTo(createBalanceDTO.getDateTo())<=0)
			{
				String date =miscellaneousDto.getDate();
				date = date.substring(0, Math.min(date.length(), 10));
				miscellaneousDto.setDate(date);
				list3.add(miscellaneousDto);
			}
		}
		return list3;
	}
	
	@Override
	public List<MiscellaneousDto> getCreditMisc(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<MiscellaneousDto> allMisc = miscellaneousService.getAllMiscCredit(domainUser);
		List<MiscellaneousDto> list3 = new ArrayList<>();
		for(MiscellaneousDto miscellaneousDto : allMisc)
		{
			String str = miscellaneousDto.getDate();
			String str1;
			str1= str.substring(6,10)+"-"+str.substring(3,5)+"-"+str.substring(0,2);;
			miscellaneousDto.setDate(str1);
			System.out.println(miscellaneousDto.getDate());
			if(miscellaneousDto.getDate().compareTo(createBalanceDTO.getDateFrom())>=0 && miscellaneousDto.getDate().compareTo(createBalanceDTO.getDateTo())<=0)
			{
				String date =miscellaneousDto.getDate();
				date = date.substring(0, Math.min(date.length(), 10));
				miscellaneousDto.setDate(date);
				list3.add(miscellaneousDto);
			}
		}
		return list3;
	}


	@Override
	public Double profit_loss(List<PurchaseDto> purchaseList, List<SalesDto> salesList,List<MiscellaneousDto> miscList) {
		double sumTotal=0.0;
		for(PurchaseDto purchaseDto : purchaseList)
		{
			sumTotal=sumTotal-purchaseDto.getTotalPrice();
		}
		for(SalesDto salesDto : salesList)
		{
			sumTotal=sumTotal+salesDto.getTotalPrice();
		}
		for(MiscellaneousDto miscellaneousDto : miscList)
		{
			sumTotal=sumTotal-Double.parseDouble(miscellaneousDto.getAmount());
		}
		return sumTotal;
	}

	@Override
	public Double totalProfitLoss(Double sum, List<MiscellaneousDto> miscRestList,List<MiscellaneousDto> miscCreditList) {
		double totalSum=sum;
		for(MiscellaneousDto miscellaneousDto : miscRestList)
		{
			totalSum=totalSum-Double.parseDouble(miscellaneousDto.getAmount());
		}
		for(MiscellaneousDto miscellaneousDto : miscCreditList)
		{
			totalSum=totalSum+Double.parseDouble(miscellaneousDto.getAmount());
		}
		
		return totalSum;
	}

	@Override
	public BalanceSheetDTO getBalanceAmount(CreateBalanceDTO createBalanceDTO, DomainUser domainUser) {
		List<PurchaseDto> list1 = getPurchase(createBalanceDTO, domainUser);
		List<SalesDto> list2 = getSale(createBalanceDTO, domainUser);
		List<MiscellaneousDto> list3 = getRestMisc(createBalanceDTO, domainUser);
		List<MiscellaneousDto> list4 = getRTOMisc(createBalanceDTO, domainUser);
		List<MiscellaneousDto> list5 = getCreditMisc(createBalanceDTO, domainUser);
		List<PurchaseDto> list6 = getFilterPurchase(createBalanceDTO, domainUser);
		List<PurchaseDto> list8=openingStock(createBalanceDTO, domainUser);
		List<PurchaseDto> list9=closingStock(createBalanceDTO, domainUser);
		List<PurchaseDto> allPurchase = purchaseService.getAllPurchaseDetails(domainUser);
		HashMap<String,Double> trading_debit_map=new HashMap<>();
		HashMap<String,Double> profitloss_debit_map=new HashMap<>();
		HashMap<String,Double> balancesheet_debit_map=new HashMap<>();
		HashMap<String,Double> trading_credit_map=new HashMap<>();
		HashMap<String,Double> profitloss_credit_map=new HashMap<>();
		HashMap<String,Double> balancesheet_credit_map=new HashMap<>();
		double purchaseAmountTotal=0.0;
		double salesAmountTotal=0.0;
		double miscellaneous_debit=0.0;
		double miscellaneous_credit=0.0;
		double miscellaneous_hsrp=0.0;
		double miscellaneous_hypo=0.0;
		double miscellaneous_registration=0.0;
		double miscellaneous_insurance=0.0;
		double miscellaneous_driving=0.0;
		double miscellaneous_credit_commission=0.0;
		double miscellaneous_petty=0.0;
		double profit=0.0;
		double stock=0.0;
		double miscellaneous_rent=0.0;
		double miscellaneous_electricity=0.0;
		double miscellaneous_salary=0.0;
		double miscellaneous_travel=0.0;
		double miscellaneous_food=0.0;
		double miscellaneous_incentives=0.0;
		double miscellaneous_commission=0.0;
		double miscellaneous_office=0.0;
		double miscellaneous_broadband=0.0;
		double miscellaneous_others=0.0;
		double pl_gross_debit=0.0;
		double pl_gross_credit=0.0;
		double pl_gross_profit_loss=0.0;
		double opening_stock=0.0;
		double closing_stock=0.0;
		double sales_gross_gst=0.0;
		double purchase_gross_gst=0.0;
		double total_liablities=0.0;
		double total_assets=0.0;
		for(PurchaseDto purchaseDto :allPurchase)
		{
			for(SalesDto salesDto : list2)
			{
				if (purchaseDto.getChassis_number().equals(salesDto.getChassis_number())) {
					profit=profit-purchaseDto.getPerQuantityPrice()-purchaseDto.getPurchase_gst();
				}
			}
		}
		for(PurchaseDto purchaseDto : list1)
		{
			purchase_gross_gst=purchase_gross_gst+purchaseDto.getPurchase_gst();
			purchaseAmountTotal=purchaseAmountTotal+purchaseDto.getPerQuantityPrice();
		}
		for(SalesDto salesDto : list2)
		{
			sales_gross_gst=sales_gross_gst+salesDto.getSales_gst();
			salesAmountTotal=salesAmountTotal+salesDto.getTotalPrice()-salesDto.getSales_gst();
			profit=profit+salesDto.getTotalPrice()-salesDto.getSales_gst()-Double.parseDouble(salesDto.getHsrfCharge())-Double.parseDouble(salesDto.getHypoCharge())
					-Double.parseDouble(salesDto.getInsuranceCharge())-Double.parseDouble(salesDto.getRegistrationCharge())-Double.parseDouble(salesDto.getLicenseCharge());
		}
		for(MiscellaneousDto miscellaneousDto : list3)
		{
			if(miscellaneousDto.getAccount_name().equals("TRADING"))
			{
				if(!trading_debit_map.containsKey(miscellaneousDto.getExpenseTypeList()))
				{
					trading_debit_map.put(miscellaneousDto.getExpenseTypeList(),Double.parseDouble(miscellaneousDto.getAmount()));
					total_liablities=total_liablities+Double.parseDouble(miscellaneousDto.getAmount());
				}
				else
				{
					double x=trading_debit_map.get(miscellaneousDto.getExpenseTypeList());
					x=x+Double.parseDouble(miscellaneousDto.getAmount());
					total_liablities=total_liablities+Double.parseDouble(miscellaneousDto.getAmount());
					trading_debit_map.put(miscellaneousDto.getExpenseTypeList(),x);
				}
			}
			else if(miscellaneousDto.getAccount_name().equals("PROFIT_LOSS"))
			{
				if(!profitloss_debit_map.containsKey(miscellaneousDto.getExpenseTypeList()))
				{
					profitloss_debit_map.put(miscellaneousDto.getExpenseTypeList(),Double.parseDouble(miscellaneousDto.getAmount()));
					total_liablities=total_liablities+Double.parseDouble(miscellaneousDto.getAmount());
					pl_gross_debit=pl_gross_debit+Double.parseDouble(miscellaneousDto.getAmount());
				}
				else
				{
					double x=profitloss_debit_map.get(miscellaneousDto.getExpenseTypeList());
					x=x+Double.parseDouble(miscellaneousDto.getAmount());
					profitloss_debit_map.put(miscellaneousDto.getExpenseTypeList(),x);
					total_liablities=total_liablities+Double.parseDouble(miscellaneousDto.getAmount());
					pl_gross_debit=pl_gross_debit+Double.parseDouble(miscellaneousDto.getAmount());
				}
			}
			else
			{
				if(!balancesheet_debit_map.containsKey(miscellaneousDto.getExpenseTypeList()))
				{
					balancesheet_debit_map.put(miscellaneousDto.getExpenseTypeList(),Double.parseDouble(miscellaneousDto.getAmount()));
					total_liablities=total_liablities+Double.parseDouble(miscellaneousDto.getAmount());
				}
				else
				{
					double x=balancesheet_debit_map.get(miscellaneousDto.getExpenseTypeList());
					x=x+Double.parseDouble(miscellaneousDto.getAmount());
					total_liablities=total_liablities+Double.parseDouble(miscellaneousDto.getAmount());
					balancesheet_debit_map.put(miscellaneousDto.getExpenseTypeList(),x);
				}
			}
//			pl_gross_debit=miscellaneous_rent+miscellaneous_electricity+miscellaneous_salary+miscellaneous_travel+miscellaneous_food+
//					miscellaneous_incentives+miscellaneous_commission+miscellaneous_office+miscellaneous_broadband+miscellaneous_others;
//			miscellaneous_debit=miscellaneous_debit+Double.parseDouble(miscellaneousDto.getAmount());
		}
		for(MiscellaneousDto miscellaneousDto : list5)
		{
			if(miscellaneousDto.getAccount_name().equals("TRADING"))
			{
				if(!trading_credit_map.containsKey(miscellaneousDto.getExpenseTypeList()))
				{
					trading_credit_map.put(miscellaneousDto.getExpenseTypeList(),Double.parseDouble(miscellaneousDto.getAmount()));
					total_assets=total_assets+Double.parseDouble(miscellaneousDto.getAmount());
				}
				else
				{
					double x=trading_credit_map.get(miscellaneousDto.getExpenseTypeList());
					x=x+Double.parseDouble(miscellaneousDto.getAmount());
					total_assets=total_assets+Double.parseDouble(miscellaneousDto.getAmount());
					trading_credit_map.put(miscellaneousDto.getExpenseTypeList(),x);
				}
			}
			else if(miscellaneousDto.getAccount_name().equals("PROFIT_LOSS"))
			{
				if(!profitloss_credit_map.containsKey(miscellaneousDto.getExpenseTypeList()))
				{
					profitloss_credit_map.put(miscellaneousDto.getExpenseTypeList(),Double.parseDouble(miscellaneousDto.getAmount()));
					total_assets=total_assets+Double.parseDouble(miscellaneousDto.getAmount());
					pl_gross_credit=pl_gross_credit+Double.parseDouble(miscellaneousDto.getAmount());
				}
				else
				{
					double x=profitloss_credit_map.get(miscellaneousDto.getExpenseTypeList());
					x=x+Double.parseDouble(miscellaneousDto.getAmount());
					total_assets=total_assets+Double.parseDouble(miscellaneousDto.getAmount());
					pl_gross_credit=pl_gross_credit+Double.parseDouble(miscellaneousDto.getAmount());
					profitloss_credit_map.put(miscellaneousDto.getExpenseTypeList(),x);
				}
			}
			else
			{
				if(!balancesheet_credit_map.containsKey(miscellaneousDto.getExpenseTypeList()))
				{
					balancesheet_credit_map.put(miscellaneousDto.getExpenseTypeList(),Double.parseDouble(miscellaneousDto.getAmount()));
					total_assets=total_assets+Double.parseDouble(miscellaneousDto.getAmount());
				}
				else
				{
					double x=balancesheet_credit_map.get(miscellaneousDto.getExpenseTypeList());
					x=x+Double.parseDouble(miscellaneousDto.getAmount());
					total_assets=total_assets+Double.parseDouble(miscellaneousDto.getAmount());
					balancesheet_credit_map.put(miscellaneousDto.getExpenseTypeList(),x);
				}
			}
		}
//		for(MiscellaneousDto miscellaneousDto : list5)
//		{
//			if(miscellaneousDto.getExpenseTypeList().equals(ExpenseTypeList.Commission.toString()))
//			{
//				miscellaneous_credit_commission = miscellaneous_credit_commission + Double.parseDouble(miscellaneousDto.getAmount());
//			}
//			if(miscellaneousDto.getExpenseTypeList().equals(ExpenseTypeList.Petty_Cash.toString()))
//			{
//				miscellaneous_petty=miscellaneous_petty+Double.parseDouble(miscellaneousDto.getAmount());
//			}
//			pl_gross_credit=miscellaneous_credit_commission;
//				miscellaneous_credit=miscellaneous_credit+Double.parseDouble(miscellaneousDto.getAmount());
//		}
//		for(PurchaseDto purchaseDto : list6)
//		{
//			stock=stock+purchaseDto.getTotalPrice();
//		}
		for(PurchaseDto purchaseDto : list8)
		{
			opening_stock=opening_stock+purchaseDto.getPerQuantityPrice();
		}
		for(PurchaseDto purchaseDto : list9)
		{
			closing_stock=closing_stock+purchaseDto.getPerQuantityPrice()+purchaseDto.getPurchase_gst();
		}
		pl_gross_profit_loss=pl_gross_credit-pl_gross_debit+profit;
		if(pl_gross_profit_loss>=0) {
            if(profit<0)
            {
                total_liablities = total_liablities + opening_stock + purchaseAmountTotal  + pl_gross_profit_loss + purchase_gross_gst+ (profit*-1);
                total_assets = total_assets + salesAmountTotal + sales_gross_gst + closing_stock+ (profit*-1);
            }
            else {
                total_liablities = total_liablities + opening_stock + purchaseAmountTotal + profit + pl_gross_profit_loss + purchase_gross_gst;
                total_assets = total_assets + salesAmountTotal + sales_gross_gst + profit + closing_stock;
            }
		}
		else {
			if(profit<0)
			{
				total_liablities = total_liablities + opening_stock + purchaseAmountTotal + (profit*-1)  + purchase_gross_gst;
				total_assets=total_assets+salesAmountTotal+sales_gross_gst+(profit*-1)+ (pl_gross_profit_loss*-1)+closing_stock;
			}
			else {
				total_liablities = total_liablities + opening_stock + purchaseAmountTotal + profit  + purchase_gross_gst;
				total_assets=total_assets+salesAmountTotal+sales_gross_gst+profit+ (pl_gross_profit_loss*-1)+closing_stock;
			}

		}

		BalanceSheetDTO balanceSheetDTO = new BalanceSheetDTO();
		balanceSheetDTO.setPurchase_amount(purchaseAmountTotal);
		balanceSheetDTO.setSales_amount(salesAmountTotal);
		balanceSheetDTO.setMiscellaneous_expense(miscellaneous_debit);
		balanceSheetDTO.setMiscellaneous_income(miscellaneous_credit);
		balanceSheetDTO.setStock(stock);
//		balanceSheetDTO.setMiscellaneous_hsrp(miscellaneous_hsrp);
//		balanceSheetDTO.setMiscellaneous_hypo(miscellaneous_hypo);
//		balanceSheetDTO.setMiscellaneous_insurance(miscellaneous_insurance);
//		balanceSheetDTO.setMiscellaneous_registration(miscellaneous_registration);
//		balanceSheetDTO.setMiscellaneous_driving(miscellaneous_driving);
		balanceSheetDTO.setProfit(profit);
//		balanceSheetDTO.setMiscellaneous_credit_commission(miscellaneous_credit_commission);
//		balanceSheetDTO.setMiscellaneous_petty(miscellaneous_petty);
//		balanceSheetDTO.setMiscellaneous_rent(miscellaneous_rent);
//		balanceSheetDTO.setMiscellaneous_electricity(miscellaneous_electricity);
//		balanceSheetDTO.setMiscellaneous_salary(miscellaneous_salary);
//		balanceSheetDTO.setMiscellaneous_travelling(miscellaneous_travel);
//		balanceSheetDTO.setMiscellaneous_food(miscellaneous_food);
//		balanceSheetDTO.setMiscellaneous_incentive(miscellaneous_incentives);
//		balanceSheetDTO.setMiscellaneous_commission(miscellaneous_commission);
//		balanceSheetDTO.setMiscellaneous_office(miscellaneous_office);
//		balanceSheetDTO.setMiscellaneous_broadband(miscellaneous_broadband);
//		balanceSheetDTO.setMiscellaneous_others(miscellaneous_others);
		balanceSheetDTO.setPl_gross_profit_loss(pl_gross_profit_loss);
		balanceSheetDTO.setOpening_stock(opening_stock);
		balanceSheetDTO.setClosing_stock(closing_stock);
		balanceSheetDTO.setSales_gross_gst(sales_gross_gst);
		balanceSheetDTO.setPurchase_gross_gst(purchase_gross_gst);
		balanceSheetDTO.setTrading_debit_map(trading_debit_map);
		balanceSheetDTO.setTrading_credit_map(trading_credit_map);
		balanceSheetDTO.setProfitloss_debit_map(profitloss_debit_map);
		balanceSheetDTO.setProfitloss_credit_map(profitloss_credit_map);
		balanceSheetDTO.setBalancesheet_debit_map(balancesheet_debit_map);
		balanceSheetDTO.setBalancesheet_credit_map(balancesheet_credit_map);
		balanceSheetDTO.setTotal_liablities(total_liablities);
		balanceSheetDTO.setTotal_assets(total_assets);
		return balanceSheetDTO;

	}

	
	

}
