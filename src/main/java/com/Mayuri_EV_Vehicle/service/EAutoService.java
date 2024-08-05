package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoDto;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.EAutoDto;
import com.Mayuri_EV_Vehicle.entity.EAuto;
import com.Mayuri_EV_Vehicle.entity.Vehicle;

import java.util.List;

public interface EAutoService {
    EAutoDto createEAuto(CreateEAutoDto createEAutoDto, DomainUser domainUser);

    List<EAutoDto> getAll(DomainUser domainUser);

    EAutoDto getEAutoById(String id);

    EAuto getAutoByAutoId(String id);

    EAutoDto updateERiksha(String id, CreateEAutoDto createEAutoDto);

    void deleteEAuto(String id);

    List<EAutoDto> getByVehicleId(CreateEAutoDto createEAutoDto);

    void save(CreatePurchaseDto createPurchaseDto);

    void deleteFromEAuto(CreateSalesDto createSalesDto);
}
