package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateMiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.MiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.SalesDto;
import com.Mayuri_EV_Vehicle.entity.Miscellaneous;

public interface MiscellaneousService {
	
	MiscellaneousDto saveMiscellaneous(CreateMiscellaneousDto createMiscellaneousDto,DomainUser domainUser);
	
	List<MiscellaneousDto> getAllMiscSales(DomainUser domainUser);
	
	public List<MiscellaneousDto> getAllMiscCredit(DomainUser domainUser);
	
	public List<MiscellaneousDto> getAllMiscDebit(DomainUser domainUser);
	
	public List<MiscellaneousDto> getRTOMiscDebit(DomainUser domainUser);
	
	public List<MiscellaneousDto> getRestMiscDebit(DomainUser domainUser);
	
	public List<Miscellaneous> getAllMisc();
	
	public List<MiscellaneousDto> getAllMiscDetail();
	
	public void deleteMiscById(String id);

	byte[] downloadSalesData(DomainUser domainUser);
	
}
