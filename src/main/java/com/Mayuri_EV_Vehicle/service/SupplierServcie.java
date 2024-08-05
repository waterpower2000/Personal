package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.SupplierDto;

import java.util.List;

public interface SupplierServcie {
    SupplierDto save(SupplierDto supplierDto);

    List<SupplierDto> getAll();

    SupplierDto getById(String id);

    SupplierDto save(CreatePurchaseDto createPurchaseDto);
}
