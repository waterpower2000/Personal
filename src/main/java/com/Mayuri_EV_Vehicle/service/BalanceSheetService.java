package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.BalanceSheetDTO;
import com.Mayuri_EV_Vehicle.dto.CreateBalanceDTO;
import com.Mayuri_EV_Vehicle.dto.CreateMiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.MiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.PurchaseDto;
import com.Mayuri_EV_Vehicle.dto.SalesDto;

public interface BalanceSheetService {
	
	List<PurchaseDto> getPurchase(CreateBalanceDTO createBalanceDTO,DomainUser domainUser);
	
	List<SalesDto> getSale(CreateBalanceDTO createBalanceDTO,DomainUser domainUser);
	
	List<MiscellaneousDto> getRTOMisc(CreateBalanceDTO createBalanceDTO,DomainUser domainUser);
	
	List<MiscellaneousDto> getRestMisc(CreateBalanceDTO createBalanceDTO,DomainUser domainUser);
	
	List<MiscellaneousDto> getCreditMisc(CreateBalanceDTO createBalanceDTO, DomainUser domainUser);
	
	Double profit_loss(List<PurchaseDto> purchaseList,List<SalesDto> salesList,List<MiscellaneousDto> miscList);

	Double totalProfitLoss(Double sum,List<MiscellaneousDto> miscRestList,List<MiscellaneousDto> miscCreditList);

	BalanceSheetDTO getBalanceAmount(CreateBalanceDTO createBalanceDTO, DomainUser domainUser);

	List<PurchaseDto> getFilterPurchase(CreateBalanceDTO createBalanceDTO, DomainUser domainUser);

	List<PurchaseDto> openingStock(CreateBalanceDTO createBalanceDTO, DomainUser domainUser);

    List<PurchaseDto> closingStock(CreateBalanceDTO createBalanceDTO, DomainUser domainUser);
}
