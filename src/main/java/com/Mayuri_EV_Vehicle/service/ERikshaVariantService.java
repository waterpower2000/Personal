package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateERikshaVariantsDto;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantsDto;
import com.Mayuri_EV_Vehicle.dto.ERikshaVariantsDto;
import com.Mayuri_EV_Vehicle.entity.ERiksha;
import com.Mayuri_EV_Vehicle.entity.ERikshaVariants;

import java.util.List;

public interface ERikshaVariantService {
//    ERikshaVariants createVariant(String eRikshaId, ERikshaVariants eRikshaVariants);
    ERikshaVariantsDto createVariants(CreateERikshaVariantsDto createERikshaVariantsDto);

    List<ERikshaVariantsDto> getAllVariants();

    ERikshaVariantsDto getVariantById(String variantId);

    ERikshaVariants getvariantById(String variantId);
    
//    List<ERikshaVariantsDto> getAllVariantsById(String rikshaId);

    void deleteById(String variantId);

    ERikshaVariantsDto updateVariants(String variantId, CreateERikshaVariantsDto createERikshaVariantsDto);

    void save(ERiksha save1, CreatePurchaseDto createPurchaseDto);

    void delete(ERiksha save, CreateSalesDto createSalesDto);
}
