package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import org.springframework.data.domain.Page;

import java.security.DomainCombiner;
import java.util.List;

public interface EAutoVariantDetailsService {
    EAutoVariantDetailsDto createVariantDetail(CreateEAutoVariantDetailsDto createEAutoVariantDetailsDto, DomainUser domainUser);

    List<EAutoVariantDetailsDto> getAllVariants(DomainUser domainUser);
    
    List<EAutoVariantDetailsDto> getFilterVariants(DomainUser domainUser);

    //Page<EAutoVariantDetailsDto>getpaginated(int pageno,int pagesize,DomainUser domainUser);

    EAutoVariantDetailsDto getVariantsDetailsById(String variantDetailsId);

    EAutoVariantDetails getVariantsDetails(String variantDetailsId);

    EAutoVariantDetailsDto upadteVariantDetails(String variantDetailsId, CreateEAutoVariantDetailsDto createEAutoVariantDetailsDto);

    void deleteVariantDetails(String variantDetailsId);

    void save(EAutoVariants save, CreatePurchaseDto createPurchaseDto);

    void getByChessisNumber(CreateSalesDto createSalesDto);
}
