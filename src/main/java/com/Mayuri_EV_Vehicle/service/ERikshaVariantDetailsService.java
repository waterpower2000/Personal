package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateERikshaVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.ERikshaVariantDetailsDto;
import com.Mayuri_EV_Vehicle.entity.ERikshaVariants;

import java.util.List;

public interface ERikshaVariantDetailsService {
    ERikshaVariantDetailsDto createVariantDetail(CreateERikshaVariantDetailsDto createERikshaVariantDetailsDto);

    List<ERikshaVariantDetailsDto> getAllVariants();

    ERikshaVariantDetailsDto getVariantsDetailsById(String variantDetailsId);

    void deleteVariantDetails(String variantDetailsId);

    ERikshaVariantDetailsDto upadteVariantDetails(String variantDetailsId, CreateERikshaVariantDetailsDto createERikshaVariantDetailsDto);

    void save(ERikshaVariants save, CreatePurchaseDto createPurchaseDto);

    void getByChessisNumber(CreateSalesDto createSalesDto);
}
