package com.Mayuri_EV_Vehicle.service;

import java.util.*;

import com.Mayuri_EV_Vehicle.dto.MiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.SalesDto;
import org.springframework.stereotype.Service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.entity.MainLedger;
import com.Mayuri_EV_Vehicle.entity.Purchase;
import com.Mayuri_EV_Vehicle.entity.Sales;
import com.Mayuri_EV_Vehicle.repository.MainLedgerRepository;

@Service
public class MainLedgerServiceImpl implements MainLedgerService{
	private MainLedgerRepository mainLedgerRepository;
	private PurchaseService purchaseService;
	private SalesService salesService;
	private MiscellaneousService miscellaneousService;
	
	

	public MainLedgerServiceImpl(MainLedgerRepository mainLedgerRepository, PurchaseService purchaseService,
			SalesService salesService,MiscellaneousService miscellaneousService) {
		super();
		this.mainLedgerRepository = mainLedgerRepository;
		this.purchaseService = purchaseService;
		this.salesService = salesService;
		this.miscellaneousService = miscellaneousService;
	}



	@Override
	public List<MainLedger> getAllMainLedger(DomainUser domainUser) {
		List<MainLedger> mainLedger1=new ArrayList<>();
		List<Purchase> allPurchase = purchaseService.getAllPurchase(domainUser);
		List<SalesDto> allSales = salesService.getAllSales(domainUser);
		List<MiscellaneousDto> miscSales = miscellaneousService.getAllMiscSales(domainUser);
		int sizeOfPurchase=0;
		int sizeOfSales=0;
		for(Purchase purchase : allPurchase) {
			MainLedger mainLedger =new MainLedger();
			mainLedger.setId(purchase.getId());
			mainLedger.setType("DEBIT");
			mainLedger.setDate(purchase.getPurchaseDate().toString());
			mainLedger.setTotalAmount(purchase.getTotalPrice());
			mainLedger1.add(mainLedger);
			sizeOfPurchase = mainLedger1.size();
		}
		
		for(SalesDto sale : allSales) {
			MainLedger mainLedger =new MainLedger();
			mainLedger.setId(sale.getId());
			mainLedger.setType("CREDIT");
			mainLedger.setDate(sale.getPurchaseDate().toString());
			mainLedger.setTotalAmount(sale.getTotalPrice());
			mainLedger1.add(mainLedger);
			sizeOfSales = mainLedger1.size()-sizeOfPurchase;
		}
		for(MiscellaneousDto miscellaneousDto : miscSales) {
			MainLedger mainLedger =new MainLedger();
			mainLedger.setId(miscellaneousDto.getId());
			mainLedger.setType(miscellaneousDto.getMoney_type());
			mainLedger.setDate(miscellaneousDto.getDate());
			mainLedger.setTotalAmount(Double.parseDouble(miscellaneousDto.getAmount()));
			mainLedger1.add(mainLedger);
			sizeOfSales = mainLedger1.size()-sizeOfPurchase;
		}
		return mainLedger1;
	}



	@Override
	public List<MainLedger> setMainLedger(List<Sales> byRegion, List<Purchase> byRegion2) {
		List<MainLedger> mainLedger1=new ArrayList<>();
		for(Purchase purchase : byRegion2) {
			MainLedger mainLedger =new MainLedger();
			mainLedger.setId(purchase.getId());
			mainLedger.setType("DEBIT");
			mainLedger.setDate(purchase.getPurchaseDate().toString());
			mainLedger.setTotalAmount(1100000.0);
			mainLedger1.add(mainLedger);
		}
		for(Sales sale : byRegion) {
			MainLedger mainLedger =new MainLedger();
			mainLedger.setId(sale.getId());
			mainLedger.setType("CREDIT");
			mainLedger.setDate(sale.getSaleDate().toString());
			mainLedger.setTotalAmount(sale.getSales_totalPrice());
			mainLedger1.add(mainLedger);
		}
		return mainLedger1;
	}
	
	

}
