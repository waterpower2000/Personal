package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoVariantsDto;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantsDto;
import com.Mayuri_EV_Vehicle.entity.EAuto;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;

import java.util.List;

public interface EAutoVariantsService {
    EAutoVariantsDto createVariants(CreateEAutoVariantsDto createEAutoVariantsDto);

    List<EAutoVariantsDto> getAllVariants(DomainUser domainUser);

    List<EAutoVariantsDto> getAllVariantsById(String autoId);

    EAutoVariantsDto getVariantById(String variantId);

    void deleteById(String variantId);

    EAutoVariantsDto updateVariants(String variantId, CreateEAutoVariantsDto createEAutoVariantsDto);

    EAutoVariants get_variant_by_id(String eautoVariantDetailsId);

    void save(EAuto save1, CreatePurchaseDto createPurchaseDto);

    void delete(EAuto save, CreateSalesDto createSalesDto);

    List<EAutoVariantsDto> getAllfilterVariants(DomainUser domainUser);
}
