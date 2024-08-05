package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.*;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantDetailsRepository;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantsRepository;
import com.Mayuri_EV_Vehicle.repository.PurchaseRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EAutoVariantsServiceImpl implements  EAutoVariantsService{
    private EAutoVariantsRepository eAutoVariantsRepository;
    private EAutoVariantDetailsService eAutoVariantDetailsService;
    private final VehicleService vehicleService;
    private PurchaseService purchaseService;
    private PurchaseRepository purchaseRepository;
    private EAutoVariantDetailsRepository eAutoVariantDetailsRepository;
    public EAutoVariantsServiceImpl(EAutoVariantsRepository eAutoVariantsRepository,
                                    @Lazy EAutoVariantDetailsService eAutoVariantDetailsService, VehicleService vehicleService,@Lazy PurchaseService purchaseService,@Lazy PurchaseRepository purchaseRepository,@Lazy EAutoVariantDetailsRepository eAutoVariantDetailsRepository) {
        this.eAutoVariantsRepository = eAutoVariantsRepository;
        this.eAutoVariantDetailsService = eAutoVariantDetailsService;
        this.vehicleService = vehicleService;
        this.purchaseService=purchaseService;
        this.purchaseRepository=purchaseRepository;
        this.eAutoVariantDetailsRepository = eAutoVariantDetailsRepository;
    }
    @Override
    public EAutoVariantsDto createVariants(CreateEAutoVariantsDto createEAutoVariantsDto) {
//        Vehicle eAuto = vehicleService.getvehicleById(createEAutoVariantsDto.getVehicleId());
//        EAutoVariants eAutoVariants = new EAutoVariants(UUID.randomUUID().toString(),createEAutoVariantsDto.getColor(),
//                createEAutoVariantsDto.getPrice(),createEAutoVariantsDto.getFeatures(),eAuto,);
//        EAutoVariants save = eAutoVariantsRepository.save(eAutoVariants);
        return null;
    }

    @Override
    public List<EAutoVariantsDto> getAllVariants(DomainUser domainUser) {
         List<EAutoVariants> all = eAutoVariantsRepository.findAll();
         List<EAutoVariantsDto> filteredVehicleDto=new ArrayList<>();
        if(all.size() != 0) {
            if (domainUser.getRegion_name().equals("ALL")) {
                return all.stream().map(this::convertToDto).collect(Collectors.toList());
            }

            for (EAutoVariants eAutoVariants : all) {
                if (eAutoVariants.getRegion_name().equals(domainUser.getRegion_name())) {
                    filteredVehicleDto.add(convertToDto(eAutoVariants));
                }
            }
        }
        return filteredVehicleDto;
    }

    public List<EAutoVariantsDto> getAllfilterVariants(DomainUser domainUser)
    {
        List<Purchase> allpercahse = purchaseRepository.findAll();
        List<EAutoVariantDetails> EAutoVariantDetailslist=eAutoVariantDetailsRepository.findAll();
        List<EAutoVariants> filter = new ArrayList<>();
        List<EAutoVariantsDto> filteredVehicleDto=new ArrayList<>();
        int flag=0;
        for(EAutoVariantDetails eAutoVariantDetails : EAutoVariantDetailslist)  {
            for(Purchase purchase: allpercahse)  {
                if (purchase.getEAutoVariantDetails().equals(eAutoVariantDetails.getId()))
                {
                    flag=1;
                }
            }
            if(flag==0) {
                filter.add(eAutoVariantDetails.getEAutoVariants());
            }
            flag=0;
        }
        if (domainUser.getRegion_name().equals("ALL")) {
            return filter.stream().map(this::convertToDto).collect(Collectors.toList());
        }
        for (EAutoVariants eAutoVariants : filter) {
            if (eAutoVariants.getRegion_name().equals(domainUser.getRegion_name())) {
                filteredVehicleDto.add(convertToDto(eAutoVariants));
            }
        }
        return filteredVehicleDto;
    }
    @Override
    public List<EAutoVariantsDto> getAllVariantsById(String autoId) {
        List<EAutoVariants> allById = eAutoVariantsRepository.findAllById(Collections.singleton(autoId));
        Iterable<EAutoVariants> iterableStrings = allById;
        List<EAutoVariantsDto> allVehicleDto=new ArrayList<>();

        for (EAutoVariants eAutoVariants : iterableStrings) {
            EAutoVariantsDto eAutoVariantsDto = convertToDto(eAutoVariants);
            allVehicleDto.add(eAutoVariantsDto);
        }
        return allVehicleDto;
    }
    @Override
    public EAutoVariantsDto getVariantById(String variantId) {
        Optional<EAutoVariants> byId = eAutoVariantsRepository.findById(variantId);
        EAutoVariants eAutoVariants = byId.get();
        EAutoVariantsDto eAutoVariantsDto = convertToDto(eAutoVariants);
        return eAutoVariantsDto;
    }
    @Override
    public void deleteById(String variantId) {
        eAutoVariantsRepository.deleteById(variantId);
    }
    @Override
    public EAutoVariantsDto updateVariants(String variantId, CreateEAutoVariantsDto createEAutoVariantsDto) {
        Optional<EAutoVariants> byId = eAutoVariantsRepository.findById(variantId);
        EAutoVariants eAutoVariants = byId.get();
        eAutoVariants.setColor(createEAutoVariantsDto.getColor());
        eAutoVariants.setPrice(createEAutoVariantsDto.getPrice());
        eAutoVariants.setFeatures(createEAutoVariantsDto.getFeatures());
        EAutoVariants save = eAutoVariantsRepository.save(eAutoVariants);
        EAutoVariantsDto eAutoVariantsDto = convertToDto(save);
        return eAutoVariantsDto;
    }

    private EAutoVariantsDto convertToDto(EAutoVariants save) {
        EAutoVariantsDto eAutoVariantsDto = new EAutoVariantsDto();
        eAutoVariantsDto.setId(save.getId());
        eAutoVariantsDto.setVariantName(save.getVariantName());
        eAutoVariantsDto.setColor(save.getColor());
        eAutoVariantsDto.setPrice(save.getPrice());
        eAutoVariantsDto.setFeatures(save.getFeatures());
        return eAutoVariantsDto;
    }


    @Override
    public EAutoVariants get_variant_by_id(String eautoVariantDetailsId) {
        Optional<EAutoVariants> byId = eAutoVariantsRepository.findById(eautoVariantDetailsId);
        EAutoVariants eAutoVariants = byId.get();
        return eAutoVariants;
    }

    @Override
    public void save(EAuto save1, CreatePurchaseDto createPurchaseDto) {
//        EAutoVariants eAutoVariants=new EAutoVariants();
//        eAutoVariants.setId(UUID.randomUUID().toString());
//        eAutoVariants.setColor(createPurchaseDto.getColor());
//        eAutoVariants.setPrice(createPurchaseDto.getPurchasePrice());
//        eAutoVariants.setEAuto(save1);
//        EAutoVariants save = eAutoVariantsRepository.save(eAutoVariants);
//        eAutoVariantDetailsService.save(save,createPurchaseDto);
    }

    @Override
    public void delete(EAuto save, CreateSalesDto createSalesDto) {
//        List<EAutoVariants> byEAutoId = eAutoVariantsRepository.findByEAutoId(save.getId());
        eAutoVariantDetailsService.getByChessisNumber(createSalesDto);

    }


}
