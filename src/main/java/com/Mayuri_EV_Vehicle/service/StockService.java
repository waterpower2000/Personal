package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateStockTransferDto;

public interface StockService {
	
	public void changeRegion(CreateStockTransferDto createStockTransferDto,DomainUser domainUser);

}
