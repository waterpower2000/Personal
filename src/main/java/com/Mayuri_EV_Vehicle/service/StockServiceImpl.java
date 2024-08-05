package com.Mayuri_EV_Vehicle.service;

import org.springframework.stereotype.Service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.CreateStockTransferDto;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.entity.Vehicle;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantDetailsRepository;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantsRepository;
import com.Mayuri_EV_Vehicle.repository.SalesRepository;
import com.Mayuri_EV_Vehicle.repository.VehicleRepository;

@Service
public class StockServiceImpl implements StockService {
	private final EAutoVariantsService eAutoVariantsService;
	 private final EAutoVariantDetailsService eAutoVariantDetailsService;
	 private final VehicleService vehicleService;
	 private EAutoVariantDetailsRepository eAutoVariantDetailsRepository;
	 private EAutoVariantsRepository eAutoVariantsRepository;
	 private VehicleRepository vehicleRepository;
	 
	 
	 public StockServiceImpl(EAutoVariantsService eAutoVariantsService,EAutoVariantDetailsService eAutoVariantDetailsService,VehicleService vehicleService, EAutoVariantDetailsRepository eAutoVariantDetailsRepository, EAutoVariantsRepository eAutoVariantsRepository,VehicleRepository vehicleRepository)
	 {
		 this.eAutoVariantsService = eAutoVariantsService;
		 this.eAutoVariantDetailsService = eAutoVariantDetailsService;
		 this.vehicleService = vehicleService;
		 this.eAutoVariantDetailsRepository = eAutoVariantDetailsRepository;
		 this.eAutoVariantsRepository = eAutoVariantsRepository;
		 this.vehicleRepository = vehicleRepository;
	 }
	
	
	public void changeRegion(CreateStockTransferDto createStockTransferDto,DomainUser domainUser) {
		EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(createStockTransferDto.getEriksha_variant_details_id());
		eAutoVariantDetails.setRegion_name(createStockTransferDto.getRegion());
		EAutoVariants eAutoVariants = eAutoVariantsService.get_variant_by_id(eAutoVariantDetails.getEAutoVariants().getId());
		eAutoVariants.setRegion_name(createStockTransferDto.getRegion());
		Vehicle vehicle = vehicleService.getvehicleById(eAutoVariants.getEAuto().getId());
		vehicle.setRegion_name(createStockTransferDto.getRegion());
		eAutoVariantDetailsRepository.save(eAutoVariantDetails);
		eAutoVariantsRepository.save(eAutoVariants);
		vehicleRepository.save(vehicle);
    }

}
