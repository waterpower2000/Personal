package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateERikshaVariantsDto;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.ERikshaVariantsDto;
import com.Mayuri_EV_Vehicle.entity.ERiksha;
import com.Mayuri_EV_Vehicle.entity.ERikshaVariants;
import com.Mayuri_EV_Vehicle.entity.Vehicle;
import com.Mayuri_EV_Vehicle.repository.ERikshaVariantsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ERikshaVariantServiceImpl implements ERikshaVariantService{
    private ERikshaVariantsRepository eRikshaVariantsRepository;

    private final VehicleService vehicleService;
    private ERikshaVariantDetailsService eRikshaVariantDetailsService;

    public ERikshaVariantServiceImpl(ERikshaVariantsRepository eRikshaVariantsRepository,

                                     VehicleService vehicleService, ERikshaVariantDetailsService eRikshaVariantDetailsService) {
        this.eRikshaVariantsRepository = eRikshaVariantsRepository;
        this.vehicleService = vehicleService;

        this.eRikshaVariantDetailsService=eRikshaVariantDetailsService;
    }

    @Override
    public ERikshaVariantsDto createVariants(CreateERikshaVariantsDto createERikshaVariantsDto) {
       ERikshaVariants eRikshaVariants = new ERikshaVariants(createERikshaVariantsDto.getVariantName(), createERikshaVariantsDto.getColor(), createERikshaVariantsDto.getPrice(),createERikshaVariantsDto.getFeatures(), vehicleService.getvehicleById(createERikshaVariantsDto.getVehicleId()));
        ERikshaVariants save = eRikshaVariantsRepository.save(eRikshaVariants);
        return null;
    }

    @Override
    public List<ERikshaVariantsDto> getAllVariants() {
        List<ERikshaVariants> all = eRikshaVariantsRepository.findAll();
        List<ERikshaVariantsDto> allVehicleDto=new ArrayList<>();

        for (ERikshaVariants eRikshaVariants : all) {
            ERikshaVariantsDto eRikshaVariantsDto = convertToDto(eRikshaVariants);
            allVehicleDto.add(eRikshaVariantsDto);
        }
        return allVehicleDto;
    }

    @Override
    public ERikshaVariantsDto getVariantById(String variantId) {
        Optional<ERikshaVariants> byId = eRikshaVariantsRepository.findById(variantId);
        ERikshaVariants eRikshaVariants = byId.get();
        ERikshaVariantsDto eRikshaVariantsDto = convertToDto(eRikshaVariants);
        return eRikshaVariantsDto;
    }

    @Override
    public ERikshaVariants getvariantById(String variantId) {
        Optional<ERikshaVariants> byId = eRikshaVariantsRepository.findById(variantId);
        ERikshaVariants eRikshaVariants = byId.get();
        return eRikshaVariants;
    }

    @Override
    public void deleteById(String variantId) {
        eRikshaVariantsRepository.deleteById(variantId);
    }

    @Override
    public ERikshaVariantsDto updateVariants(String variantId, CreateERikshaVariantsDto createERikshaVariantsDto) {
        Optional<ERikshaVariants> byId = eRikshaVariantsRepository.findById(variantId);
        ERikshaVariants eRikshaVariants = byId.get();
        eRikshaVariants.setColor(createERikshaVariantsDto.getColor());
        eRikshaVariants.setPrice(createERikshaVariantsDto.getPrice());
        eRikshaVariants.setFeatures(createERikshaVariantsDto.getFeatures());
        ERikshaVariants save = eRikshaVariantsRepository.save(eRikshaVariants);
        return convertToDto(save);
    }

    @Override
    public void save(ERiksha save1, CreatePurchaseDto createPurchaseDto) {
//        ERikshaVariants eRikshaVariants=new ERikshaVariants();
//        eRikshaVariants.setId(UUID.randomUUID().toString());
//        eRikshaVariants.setColor(createPurchaseDto.getColor());
//        eRikshaVariants.setPrice(createPurchaseDto.getPurchasePrice());
//        eRikshaVariants.setERiksha(save1);
//        ERikshaVariants save = eRikshaVariantsRepository.save(eRikshaVariants);
//        eRikshaVariantDetailsService.save(save,createPurchaseDto);
    }

    @Override
    public void delete(ERiksha save, CreateSalesDto createSalesDto) {
        eRikshaVariantDetailsService.getByChessisNumber(createSalesDto);
    }

    private ERikshaVariantsDto convertToDto(ERikshaVariants save) {
        ERikshaVariantsDto eRikshaVariantsDto = new ERikshaVariantsDto();
        eRikshaVariantsDto.setVariantId(save.getId());
        eRikshaVariantsDto.setVariantName(save.getVariantName());
        eRikshaVariantsDto.setColor(save.getColor());
        eRikshaVariantsDto.setPrice(save.getPrice());
        eRikshaVariantsDto.setFeatures(save.getFeatures());
        return eRikshaVariantsDto;
    }

//	@Override
//	public List<ERikshaVariantsDto> getAllVariantsById(String rikshaId) {
//		List<ERikshaVariants> findBye_riksha_id = eRikshaVariantsRepository.findBye_riksha_id(rikshaId);
//		List<ERikshaVariantsDto> allVehicleDto=new ArrayList<>();
//		for (ERikshaVariants  eRikshaVariants: findBye_riksha_id) {
//	    ERikshaVariantsDto convertToDto = convertToDto(eRikshaVariants);
//	      allVehicleDto.add(convertToDto);
//		
//	}
//		return allVehicleDto;
//	}
}
