package com.Mayuri_EV_Vehicle.service;

import java.util.ArrayList;
import java.util.List;

import com.Mayuri_EV_Vehicle.dto.SalesDto;
import com.Mayuri_EV_Vehicle.entity.*;
import org.springframework.stereotype.Service;

import com.Mayuri_EV_Vehicle.config.DomainUser;

@Service
public class InventoryServiceImpl implements InventoryService{
	
	private PurchaseService purchaseService;
	private EAutoVariantDetailsService eAutoVariantDetailsService;
	private SalesService salesService;
	
	public InventoryServiceImpl(PurchaseService purchaseService, SalesService salesService) {
		this.purchaseService = purchaseService;
		this.salesService = salesService;
	}

	@Override
	public List<InventoryDetails> getDetails(DomainUser domainUser) {
		List<InventoryDetails> inventoryDetails1=new ArrayList<>();
		List<Purchase> allPurchase = purchaseService.getAllPurchase(domainUser);
		List<SalesDto> allSales = salesService.getAllSales(domainUser);

		for(Purchase purchase : allPurchase) {
			if(purchase.getType().equals(TypeList.Vehicle)) {
				EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(purchase.getEAutoVariantDetails());
				InventoryDetails inventoryDetails = new InventoryDetails();
				inventoryDetails.setId(purchase.getId());
				inventoryDetails.setCustomer_Suppiler_Name(purchase.getSupplierName());
				inventoryDetails.setCategory("Vehicle");
				inventoryDetails.setSubCategory(eAutoVariantDetails.getEAutoVariants().getEAuto().getVechileType().toString());
				inventoryDetails.setVariants(eAutoVariantDetails.getEAutoVariants().getVariantName());
				inventoryDetails.setTransactionType("Purchase");
				inventoryDetails1.add(inventoryDetails);
			}
		}		
		for(SalesDto sale : allSales) {
			if(sale.getPurchaseType().equals(TypeList.Vehicle.name())) {
				EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(sale.getEriksha_variant_details_id());
				InventoryDetails inventoryDetails = new InventoryDetails();
				inventoryDetails.setId(sale.getId());
				inventoryDetails.setCustomer_Suppiler_Name(sale.getCustomer_name());
				inventoryDetails.setCategory("Vehicle");
				;
				inventoryDetails.setSubCategory(eAutoVariantDetails.getEAutoVariants().getEAuto().getVechileType().toString());
				inventoryDetails.setVariants(eAutoVariantDetails.getEAutoVariants().getVariantName());
				inventoryDetails.setTransactionType("Sale");
				inventoryDetails1.add(inventoryDetails);
			}
		}
		return inventoryDetails1;
	}
}
