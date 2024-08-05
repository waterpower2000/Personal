package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreatePartPaymentDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.PartPaymentDetailsDTO;
import com.Mayuri_EV_Vehicle.dto.SalesDto;
import com.Mayuri_EV_Vehicle.entity.DashBoard;
import com.Mayuri_EV_Vehicle.entity.Sales;

import java.util.List;

public interface SalesService {

    void updatePart(CreatePartPaymentDto createPartPaymentDto, String id);

    SalesDto saveOneSales(CreateSalesDto createSalesDto, DomainUser domainUser);

    List<SalesDto> getAll();

    SalesDto getById(String salesId);
    
    SalesDto editById(String salesId);

    void deleteById(String salesId);

    SalesDto UpdateSales(String salesId,CreateSalesDto createSalesDto,DomainUser domainUser);

	List<SalesDto> getAllSales(DomainUser domainUser);
	
	List<SalesDto> getAllFullPaymentSales(DomainUser domainUser);

	List<Sales> getByRegion(String region,DashBoard dashBoard);
	
	byte[] downloadSalesData(String reg);

    List<PartPaymentDetailsDTO> getPartPaymentDetails(DomainUser domainUser, String id);
}
