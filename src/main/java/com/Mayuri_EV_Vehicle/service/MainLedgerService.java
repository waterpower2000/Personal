package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.entity.MainLedger;
import com.Mayuri_EV_Vehicle.entity.Purchase;
import com.Mayuri_EV_Vehicle.entity.Sales;

public interface MainLedgerService {

	List<MainLedger> getAllMainLedger(DomainUser domainUser);

	List<MainLedger> setMainLedger(List<Sales> byRegion, List<Purchase> byRegion2);

}
