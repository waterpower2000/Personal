package com.Mayuri_EV_Vehicle.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.entity.DashBoard;
import com.Mayuri_EV_Vehicle.entity.DashLedger;
import com.Mayuri_EV_Vehicle.entity.MainLedger;
import com.Mayuri_EV_Vehicle.entity.Purchase;
import com.Mayuri_EV_Vehicle.entity.Sales;

@Service
public class DashBoardServiceImpl implements DashBoardService{
	
	private SalesService salesService;
	private PurchaseService purchaseService;
	private MainLedgerService mainLedgerService;
	

	public DashBoardServiceImpl(SalesService salesService, PurchaseService purchaseService,
			MainLedgerService mainLedgerService) {
		this.salesService = salesService;
		this.purchaseService = purchaseService;
		this.mainLedgerService = mainLedgerService;
	}


	@Override
	public List<?> create(DashBoard dashBoard, DomainUser domainUser) {
		List<DashBoard> dashBoard1=new ArrayList<>();
		List<Sales> byRegion = salesService.getByRegion(dashBoard.getRegion(),dashBoard);
		List<Purchase> byRegion2 = purchaseService.getByRegion(dashBoard.getRegion(),dashBoard);
		List<MainLedger> setMainLedger = mainLedgerService.setMainLedger(byRegion,byRegion2);
		List<DashLedger> dashLedger1 = new ArrayList<>();
		DashLedger dashLedger= new DashLedger();
		if(dashBoard.getLed_cat().equals("1")) {
			for(Sales sale: byRegion) {
				dashLedger.setId(sale.getId());
				dashLedger.setType("Sale");
				dashLedger.setDate(sale.getSaleDate().toString());
				dashLedger.setTotalAmount(sale.getSales_totalPrice());
				dashLedger1.add(dashLedger);
			}
			return dashLedger1;
		}
		if(dashBoard.getLed_cat().equals("2")) {
			for(Purchase purchase: byRegion2) {
				dashLedger.setId(purchase.getId());
				dashLedger.setType("Purchase");
				dashLedger.setDate(purchase.getPurchaseDate().toString());
				dashLedger.setTotalAmount(purchase.getTotalPrice());
				dashLedger1.add(dashLedger);
			}
			return dashLedger1;
		}
		else {
			return setMainLedger;
		}
	}

}
