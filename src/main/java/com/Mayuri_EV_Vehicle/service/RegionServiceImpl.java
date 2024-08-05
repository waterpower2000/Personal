package com.Mayuri_EV_Vehicle.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.entity.Purchase;
import com.Mayuri_EV_Vehicle.entity.Region;
import com.Mayuri_EV_Vehicle.entity.Sales;
import com.Mayuri_EV_Vehicle.entity.Vehicle;
import com.Mayuri_EV_Vehicle.model.RegionTable;
import com.Mayuri_EV_Vehicle.dto.CreateRegionDTO;
import com.Mayuri_EV_Vehicle.dto.PurchaseDto;
import com.Mayuri_EV_Vehicle.dto.RegionDto;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantDetailsRepository;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantsRepository;
import com.Mayuri_EV_Vehicle.repository.PurchaseRepository;
import com.Mayuri_EV_Vehicle.repository.RegionRepository;
import com.Mayuri_EV_Vehicle.repository.SalesRepository;
import com.Mayuri_EV_Vehicle.repository.VehicleRepository;



@Service
public class RegionServiceImpl implements RegionService {
	private RegionRepository regionRepository;
	private EAutoVariantDetailsService eAutoVariantDetailsService;
	private EAutoVariantsService eAutoVariantsService;
	private VehicleService vehicleService;
	private EAutoVariantDetailsRepository eAutoVariantDetailsRepository;
	private EAutoVariantsRepository eAutoVariantsRepository;
	private VehicleRepository vehicleRepository;
	private PurchaseService purchaseService;
	private PurchaseRepository purchaseRepository;
	private SalesService salesService;
	private SalesRepository salesRepository;

	public RegionServiceImpl(RegionRepository regionRepository,EAutoVariantDetailsService eAutoVariantDetailsService,EAutoVariantsService eAutoVariantsService
			,VehicleService vehicleService,EAutoVariantDetailsRepository eAutoVariantDetailsRepository,EAutoVariantsRepository eAutoVariantsRepository,VehicleRepository vehicleRepository,
			PurchaseService purchaseService,PurchaseRepository purchaseRepository,SalesService salesService,SalesRepository salesRepository) {
		this.regionRepository = regionRepository;
		this.eAutoVariantDetailsService = eAutoVariantDetailsService;
		this.eAutoVariantsService = eAutoVariantsService;
		this.vehicleService = vehicleService;
		this.eAutoVariantDetailsRepository = eAutoVariantDetailsRepository;
		this.eAutoVariantsRepository = eAutoVariantsRepository;
		this.vehicleRepository = vehicleRepository;
		this.purchaseService = purchaseService;
		this.purchaseRepository = purchaseRepository;
		this.salesService = salesService;
		this.salesRepository = salesRepository;
	}

	@Override
	public RegionDto create(CreateRegionDTO createRegionDTO) {
		RegionTable regionTable = new RegionTable(createRegionDTO.getRegionName(),createRegionDTO.getRegionDetails());
		RegionTable save = regionRepository.save(regionTable);
		return convertToDto(save);
	}

	private RegionDto convertToDto(RegionTable save) {
		RegionDto regionDto = new RegionDto(save.getRegionId(),save.getRegionName(),save.getRegionDetails());
		return regionDto;
	}

	@Override
	public List<RegionTable> getAllRegion() {
		return regionRepository.findAll();
	}
	
	public RegionTable getRegionById(String groupId) {

        Optional<RegionTable> groupsOptional = regionRepository.findById(groupId);
        if(!groupsOptional.isPresent()){
            throw new RuntimeException("No Region found from this id");
        }
        return groupsOptional.get();
    }
	
	public RegionDto updateRegion(String groupId, CreateRegionDTO createRegionDTO) {
		RegionTable existingGroup = getRegionById(groupId);
		List<Purchase> list=purchaseRepository.findAll();	
		for(Purchase purchase :list)
		{
			if(purchase.getRegion_name().equals(existingGroup.getRegionName()))
					{
						EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(purchase.getEAutoVariantDetails());
						EAutoVariants eAutoVariants = eAutoVariantsService.get_variant_by_id(eAutoVariantDetails.getEAutoVariants().getId());
						Vehicle vehicle = vehicleService.getvehicleById(eAutoVariants.getEAuto().getId());
						eAutoVariantDetails.setRegion_name(createRegionDTO.getRegionName());
						eAutoVariants.setRegion_name(createRegionDTO.getRegionName());
						vehicle.setRegion_name(createRegionDTO.getRegionName());
						purchase.setRegion_name(createRegionDTO.getRegionName());
						purchaseRepository.save(purchase);
						eAutoVariantDetailsRepository.save(eAutoVariantDetails);
						eAutoVariantsRepository.save(eAutoVariants);
						vehicleRepository.save(vehicle);
					}
		}
		
		List<Sales> all = salesRepository.findAll();
		if(all!=null) {
			for(Sales sales :all)
			{
				if(sales.getRegion_name().equals(existingGroup.getRegionName()))
				{
					sales.setRegion_name(createRegionDTO.getRegionName());
					salesRepository.save(sales);
				}
			}
		}
        existingGroup.setRegionName(createRegionDTO.getRegionName());
        existingGroup.setRegionDetails(createRegionDTO.getRegionDetails());
        return convertToDto(regionRepository.save(existingGroup));
    }

	@Override
	public RegionDto getRegionByIdDetails(String groupId) {
		return convertToDto(getRegionById(groupId));
	}

	@Override
	public List<RegionDto> getAllRegionDetail() {
		return getAllRegion().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public void deleteRegion(String id) {
		RegionTable existingGroup = getRegionById(id);
		regionRepository.delete(existingGroup);
		
	}
}
