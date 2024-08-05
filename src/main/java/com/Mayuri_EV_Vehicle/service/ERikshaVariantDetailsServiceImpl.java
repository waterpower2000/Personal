package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.entity.ERikshaVariantDetails;
import com.Mayuri_EV_Vehicle.entity.ERikshaVariants;
import com.Mayuri_EV_Vehicle.repository.ERikshaVariantDetailsRepository;
import com.Mayuri_EV_Vehicle.repository.ERikshaVariantsRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ERikshaVariantDetailsServiceImpl implements ERikshaVariantDetailsService{
    private ERikshaVariantDetailsRepository eRikshaVariantDetailsRepository;
    private ERikshaVariantsRepository eRikshaVariantsRepository;
    private ERikshaVariantService eRikshaVariantService;
    public ERikshaVariantDetailsServiceImpl(ERikshaVariantDetailsRepository eRikshaVariantDetailsRepository,
                                            ERikshaVariantsRepository eRikshaVariantsRepository,
                                            @Lazy ERikshaVariantService eRikshaVariantService) {
        this.eRikshaVariantDetailsRepository = eRikshaVariantDetailsRepository;
        this.eRikshaVariantsRepository = eRikshaVariantsRepository;
        this.eRikshaVariantService=eRikshaVariantService;
    }

    @Override
    public ERikshaVariantDetailsDto createVariantDetail(CreateERikshaVariantDetailsDto createERikshaVariantDetailsDto) {
        ERikshaVariants eRikshaVariants = eRikshaVariantService.getvariantById(createERikshaVariantDetailsDto.getEriksha_variant_details_id());
        ERikshaVariantDetails eRikshaVariantDetails= new ERikshaVariantDetails(UUID.randomUUID().toString(),
                eRikshaVariants,createERikshaVariantDetailsDto.getChassis_number(),
                createERikshaVariantDetailsDto.getModel_number(),
                createERikshaVariantDetailsDto.getEngine_number());
        ERikshaVariantDetails save = eRikshaVariantDetailsRepository.save(eRikshaVariantDetails);
        return convertToDto(save);
    }

    @Override
    public List<ERikshaVariantDetailsDto> getAllVariants() {
        List<ERikshaVariantDetails> all = eRikshaVariantDetailsRepository.findAll();
        List<ERikshaVariantDetailsDto> allVehicleDto=new ArrayList<>();

        for (ERikshaVariantDetails eRikshaVariantDetails : all) {
            ERikshaVariantDetailsDto eRikshaVariantDetailsDto = convertToDto(eRikshaVariantDetails);
            allVehicleDto.add(eRikshaVariantDetailsDto);
        }
        return allVehicleDto;
    }

    @Override
    public ERikshaVariantDetailsDto getVariantsDetailsById(String variantDetailsId) {
        Optional<ERikshaVariantDetails> byId = eRikshaVariantDetailsRepository.findById(variantDetailsId);
        ERikshaVariantDetails eRikshaVariantDetails = byId.get();
        return convertToDto(eRikshaVariantDetails);
    }

    @Override
    public void deleteVariantDetails(String variantDetailsId) {
        eRikshaVariantDetailsRepository.deleteById(variantDetailsId);
    }

    @Override
    public ERikshaVariantDetailsDto upadteVariantDetails(String variantDetailsId, CreateERikshaVariantDetailsDto createERikshaVariantDetailsDto) {
        Optional<ERikshaVariantDetails> byId = eRikshaVariantDetailsRepository.findById(variantDetailsId);
        ERikshaVariantDetails eRikshaVariantDetails = byId.get();
        eRikshaVariantDetails.setChassis_number(createERikshaVariantDetailsDto.getChassis_number());
        eRikshaVariantDetails.setModel_number(createERikshaVariantDetailsDto.getModel_number());
        eRikshaVariantDetails.setEngine_number(createERikshaVariantDetailsDto.getEngine_number());
        ERikshaVariantDetails save = eRikshaVariantDetailsRepository.save(eRikshaVariantDetails);
        return convertToDto(save);
    }

    @Override
    public void save(ERikshaVariants save, CreatePurchaseDto createPurchaseDto) {
        ERikshaVariantDetails eRikshaVariantDetails= new ERikshaVariantDetails();
        eRikshaVariantDetails.setId(UUID.randomUUID().toString());
        eRikshaVariantDetails.setERikshaVariants(save);
        eRikshaVariantDetails.setModel_number(createPurchaseDto.getModel_number());
        eRikshaVariantDetails.setEngine_number(createPurchaseDto.getEngine_number());
        eRikshaVariantDetails.setChassis_number(createPurchaseDto.getChassis_number());
        ERikshaVariantDetails save1 = eRikshaVariantDetailsRepository.save(eRikshaVariantDetails);
    }

    @Override
    public void getByChessisNumber(CreateSalesDto createSalesDto) {
        List<ERikshaVariantDetails> all = eRikshaVariantDetailsRepository.findAll();
        for(ERikshaVariantDetails eRikshaVariantDetails : all){
//            if(eRikshaVariantDetails.getChassis_number().equals(createSalesDto.getSales_chassis_number())){
//                eRikshaVariantDetailsRepository.delete(eRikshaVariantDetails);
//            }
        }
    }

    private ERikshaVariantDetailsDto convertToDto(ERikshaVariantDetails save) {
        ERikshaVariantDetailsDto eRikshaVariantDetailsDto=new ERikshaVariantDetailsDto();
        eRikshaVariantDetailsDto.setId(save.getId());
        eRikshaVariantDetailsDto.setChassis_number(save.getChassis_number());
        eRikshaVariantDetailsDto.setModel_number(save.getModel_number());
        eRikshaVariantDetailsDto.setEngine_number(save.getEngine_number());
        return eRikshaVariantDetailsDto;
    }
}
