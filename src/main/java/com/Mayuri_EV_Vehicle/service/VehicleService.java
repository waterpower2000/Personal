package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.Vehicle;

import java.util.List;

public interface VehicleService {
    List<VehicleDto> getAllVehicles(DomainUser domainUser);

    VehicleDto getVehicleById(String id);
    Vehicle getvehicleById(String id);
    VehicleDto createVehicle(CreateVehicleDTO createVehicleDTO, DomainUser domainUser);

    VehicleVariantDTO createVehicleVariant(CreateVehicleDTO createVehicleDTO, DomainUser domainUser);

    VehicleDto updateVehicle(String id, CreateVehicleDTO vehicleDTO, DomainUser domainUser);

    void deleteVehicle(String id);

    VehicleDto saveVehicle(CreatePurchaseDto createPurchaseDto);

}
