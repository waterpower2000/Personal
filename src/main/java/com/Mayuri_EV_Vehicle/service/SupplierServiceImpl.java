package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.SupplierDto;
import com.Mayuri_EV_Vehicle.entity.Supplier;
import com.Mayuri_EV_Vehicle.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierServiceImpl implements SupplierServcie{
    private SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierDto save(SupplierDto supplierDto) {
        Supplier supplier=new Supplier(UUID.randomUUID().toString(),supplierDto.getSupplier_name(),supplierDto.getSupplier_number(),supplierDto.getSupplier_AadhaarCard(),
                supplierDto.getSupplier_PanCard(), supplierDto.getSupplier_GSTNumber(), supplierDto.getSupplier_address());
        Supplier save = supplierRepository.save(supplier);
        return convertToDto(save);
    }

    @Override
    public List<SupplierDto> getAll() {
        List<Supplier> all = supplierRepository.findAll();
        List<SupplierDto> allSupplierDto= new ArrayList<>();
        for(Supplier supplier: all){
            SupplierDto supplierDto = convertToDto(supplier);
            allSupplierDto.add(supplierDto);
        }
        return allSupplierDto;
    }

    @Override
    public SupplierDto getById(String id) {
        Optional<Supplier> byId = supplierRepository.findById(id);
        Supplier supplier = byId.get();
        return convertToDto(supplier);
    }

    @Override
    public SupplierDto save(CreatePurchaseDto createPurchaseDto) {
//        Supplier supplier=new Supplier(UUID.randomUUID().toString(),createPurchaseDto.getSupplier_name(),createPurchaseDto.getSupplier_number(),createPurchaseDto.getSupplier_AadhaarCard(),
//                createPurchaseDto.getSupplier_PanCard(), createPurchaseDto.getSupplier_GSTNumber(), createPurchaseDto.getSupplier_address());
//        Supplier save = supplierRepository.save(supplier);
//        return convertToDto(save);

        return null;
    }

    public SupplierDto convertToDto(Supplier supplier){
        SupplierDto supplierDto=new SupplierDto(supplier.getId(),supplier.getSupplier_name(),supplier.getSupplier_number(),
                supplier.getSupplier_AadhaarCard(), supplier.getSupplier_PanCard(),supplier.getSupplier_GSTNumber(),
                supplier.getSupplier_address());
        return supplierDto;
    }
}
