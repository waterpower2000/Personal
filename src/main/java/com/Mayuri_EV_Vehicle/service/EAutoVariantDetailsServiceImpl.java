package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantDetailsRepository;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantsRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class EAutoVariantDetailsServiceImpl implements EAutoVariantDetailsService{
    private EAutoVariantDetailsRepository eAutoVariantDetailsRepository;
    private EAutoVariantsRepository eAutoVariantsRepository;
    private EAutoVariantsService eAutoVariantsService;
    private EAutoService eAutoService;
    private SalesService salesService;

    public EAutoVariantDetailsServiceImpl(EAutoVariantDetailsRepository eAutoVariantDetailsRepository,
                                          @Lazy EAutoVariantsRepository eAutoVariantsRepository,
                                          @Lazy EAutoVariantsService eAutoVariantsService,
                                          @Lazy EAutoService eAutoService,
                                          @Lazy SalesService salesService) {
        this.eAutoVariantDetailsRepository = eAutoVariantDetailsRepository;
        this.eAutoVariantsRepository = eAutoVariantsRepository;
        this.eAutoVariantsService=eAutoVariantsService;
        this.eAutoService=eAutoService;
        this.salesService=salesService;
    }

    @Override
    public EAutoVariantDetailsDto createVariantDetail(CreateEAutoVariantDetailsDto createEAutoVariantDetailsDto, DomainUser domainUser) {
        EAutoVariants variantById = eAutoVariantsService.get_variant_by_id(createEAutoVariantDetailsDto.getEauto_variant_details_id());
        EAutoVariantDetails eAutoVariantDetails= new EAutoVariantDetails(
                variantById,createEAutoVariantDetailsDto.getChassis_number(),
                createEAutoVariantDetailsDto.getModel_number(),createEAutoVariantDetailsDto.getEngine_number(),
                createEAutoVariantDetailsDto.getBattery_number(), createEAutoVariantDetailsDto.getBattery_maker_name(), createEAutoVariantDetailsDto.getBattery_warranty_start_date(),
                createEAutoVariantDetailsDto.getBattery_warranty_end_date(), createEAutoVariantDetailsDto.getMoter_number(), createEAutoVariantDetailsDto.getController_name(),
                createEAutoVariantDetailsDto.getOnRoadPrice()
                ,Region.valueOf(domainUser.getRegion()),domainUser.getRegion_name());
        EAutoVariantDetails save = eAutoVariantDetailsRepository.save(eAutoVariantDetails);
        return convertToDto(save);
    }

    @Override
    public List<EAutoVariantDetailsDto> getAllVariants(DomainUser domainUser) {
        List<EAutoVariantDetails> all = eAutoVariantDetailsRepository.findAll();
        List<EAutoVariantDetailsDto> filteredVehicleDto=new ArrayList<>();
        


        if(all.size() != 0) {
            if (domainUser.getRegion_name().equals("ALL")) {
                return all.stream().map(this::convertToDto).collect(Collectors.toList());
            }

            for (EAutoVariantDetails eAutoVariantsDetails : all) {
                if (eAutoVariantsDetails.getRegion_name().equals(domainUser.getRegion_name())) {
                    filteredVehicleDto.add(convertToDto(eAutoVariantsDetails));
                }
            }
        }
        return filteredVehicleDto;
    }
    
    public List<EAutoVariantDetailsDto> getFilterVariants(DomainUser domainUser) {
        List<EAutoVariantDetails> all = eAutoVariantDetailsRepository.findAll();
        List<EAutoVariantDetails> all1 = new ArrayList<>();
        List<EAutoVariantDetailsDto> filteredVehicleDto=new ArrayList<>();
        List<SalesDto> allSales = salesService.getAllSales(domainUser);
        int flag=0;

        if(all.size() != 0) {
            if (domainUser.getRegion_name().equals("ALL")) {
            	for (EAutoVariantDetails eAutoVariantsDetails : all) {
                	for(SalesDto sales: allSales)
                	{
                		if(eAutoVariantsDetails.getChassis_number().equals(sales.getChassis_number()))
                		flag =1;
                	}   
            	
                	if (flag==0) {
            		all1.add(eAutoVariantsDetails);
                	}
                	flag=0;
            	}            	
            	
            	return all1.stream().map(this::convertToDto).collect(Collectors.toList());
            }

            for (EAutoVariantDetails eAutoVariantsDetails : all) {
            	for(SalesDto sales: allSales)
            	{
            		if(eAutoVariantsDetails.getChassis_number().equals(sales.getChassis_number()))
            		flag =1;
            	}            	
            		if (eAutoVariantsDetails.getRegion_name().equals(domainUser.getRegion_name()) && flag==0) {
            			filteredVehicleDto.add(convertToDto(eAutoVariantsDetails));
            			            			
            }
            	flag=0;	
        }
       }
        return filteredVehicleDto;
    }

//    @Override
//    public Page<EAutoVariantDetailsDto> getpaginated(int pageno, int pagesize, DomainUser domainUser) {
//        return getFilterVariantsPaginated(pageno, pagesize, domainUser);
//    }

    //ChatGpt code start
//    public Page<EAutoVariantDetailsDto> getFilterVariantsPaginated(int pageno, int pagesize, DomainUser domainUser) {
//        List<EAutoVariantDetails> all = eAutoVariantDetailsRepository.findAll();
//        List<EAutoVariantDetailsDto> filteredVehicleDto = new ArrayList<>();
//        List<SalesDto> allSales = salesService.getAllSales(domainUser);
//        int flag = 0;
//
//        if (all.size() != 0) {
//            if (domainUser.getRegion_name().equals("ALL")) {
//                for (EAutoVariantDetails eAutoVariantsDetails : all) {
//                    for (SalesDto sales : allSales) {
//                        if (eAutoVariantsDetails.getChassis_number().equals(sales.getChassis_number()))
//                            flag = 1;
//                    }
//
//                    if (flag == 0) {
//                        filteredVehicleDto.add(convertToDto(eAutoVariantsDetails));
//                    }
//                    flag = 0;
//                }
//
//                return paginate(filteredVehicleDto, pageno, pagesize);
//            }
//
//            for (EAutoVariantDetails eAutoVariantsDetails : all) {
//                for (SalesDto sales : allSales) {
//                    if (eAutoVariantsDetails.getChassis_number().equals(sales.getChassis_number()))
//                        flag = 1;
//                }
//
//                if (eAutoVariantsDetails.getRegion_name().equals(domainUser.getRegion_name()) && flag == 0) {
//                    filteredVehicleDto.add(convertToDto(eAutoVariantsDetails));
//                }
//                flag = 0;
//            }
//        }
//
//        return paginate(filteredVehicleDto, pageno, pagesize);
//    }
//
//    private Page<EAutoVariantDetailsDto> paginate(List<EAutoVariantDetailsDto> list, int pageNo, int pageSize) {
//        int start = (pageNo - 1) * pageSize;
//        int end = Math.min((start + pageSize), list.size());
//
//        List<EAutoVariantDetailsDto> pageList = new ArrayList<>(list.subList(start, end));
//
//        return new PageImpl<>(pageList, PageRequest.of(pageNo - 1, pageSize), list.size());
//    }
    //ChatGpt code end
    @Override
    public EAutoVariantDetailsDto getVariantsDetailsById(String variantDetailsId) {
        Optional<EAutoVariantDetails> byId = eAutoVariantDetailsRepository.findById(variantDetailsId);
        EAutoVariantDetails eAutoVariantDetails = byId.get();
        return convertToDto(eAutoVariantDetails);
    }

    @Override
    public EAutoVariantDetails getVariantsDetails(String variantDetailsId) {
        Optional<EAutoVariantDetails> byId = eAutoVariantDetailsRepository.findById(variantDetailsId);
        if(!byId.isPresent()){
            throw new  RuntimeException("variant details with this id not found" );

        }
        EAutoVariantDetails eAutoVariantDetails = byId.get();
        return eAutoVariantDetails;
    }

    @Override
    public EAutoVariantDetailsDto upadteVariantDetails(String variantDetailsId, CreateEAutoVariantDetailsDto createEAutoVariantDetailsDto) {
        Optional<EAutoVariantDetails> byId = eAutoVariantDetailsRepository.findById(variantDetailsId);
        EAutoVariantDetails eAutoVariantDetails = byId.get();
        eAutoVariantDetails.setChassis_number(createEAutoVariantDetailsDto.getChassis_number());
        eAutoVariantDetails.setModel_number(createEAutoVariantDetailsDto.getModel_number());
        eAutoVariantDetails.setEngine_number(createEAutoVariantDetailsDto.getEngine_number());
        EAutoVariantDetails save = eAutoVariantDetailsRepository.save(eAutoVariantDetails);
        return convertToDto(save);
    }

    @Override
    public void deleteVariantDetails(String variantDetailsId) {
        eAutoVariantDetailsRepository.deleteById(variantDetailsId);
    }

    @Override
    public void save(EAutoVariants save, CreatePurchaseDto createPurchaseDto) {
        EAutoVariantDetails eAutoVariantDetails= new EAutoVariantDetails();
        eAutoVariantDetails.setId(UUID.randomUUID().toString());
        eAutoVariantDetails.setEAutoVariants(save);
        eAutoVariantDetails.setModel_number(createPurchaseDto.getModel_number());
        eAutoVariantDetails.setEngine_number(createPurchaseDto.getEngine_number());
        eAutoVariantDetails.setChassis_number(createPurchaseDto.getChassis_number());
        EAutoVariantDetails save1 = eAutoVariantDetailsRepository.save(eAutoVariantDetails);
    }

    @Override
    public void getByChessisNumber(CreateSalesDto createSalesDto) {
        List<EAutoVariantDetails> all = eAutoVariantDetailsRepository.findAll();
        for(EAutoVariantDetails eAutoVariantDetails : all){
//            if(eAutoVariantDetails.getChassis_number().equals(createSalesDto.getSales_chassis_number())){
//                eAutoVariantDetailsRepository.delete(eAutoVariantDetails);
//            }
        }
    }

    public EAutoVariantDetailsDto convertToDto(EAutoVariantDetails save){
        EAutoVariantDetailsDto eAutoVariantDetailsDto=new EAutoVariantDetailsDto();
        eAutoVariantDetailsDto.setId(save.getId());
        eAutoVariantDetailsDto.setType(save.getEAutoVariants().getEAuto().getVechileType().name());
        eAutoVariantDetailsDto.setVariantName(save.getEAutoVariants().getVariantName());
        eAutoVariantDetailsDto.setColour(save.getEAutoVariants().getColor());
        eAutoVariantDetailsDto.setPrice(save.getOnRoadPrice());
        eAutoVariantDetailsDto.setChassis_number(save.getChassis_number());
        eAutoVariantDetailsDto.setModel_number(save.getModel_number());
        eAutoVariantDetailsDto.setEngine_number(save.getEngine_number());
        eAutoVariantDetailsDto.setRegion(save.getEAutoVariants().getRegion_name());
        return eAutoVariantDetailsDto;
    }
}
