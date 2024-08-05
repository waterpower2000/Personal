package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateERikshaDto;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.ERikshaDto;
import com.Mayuri_EV_Vehicle.entity.ERiksha;
import com.Mayuri_EV_Vehicle.entity.Vehicle;

import java.util.List;

public interface ERikshaService {
    ERikshaDto createERiksha(CreateERikshaDto createERikshaDto,DomainUser domainUser);

    List<ERikshaDto> getAll(DomainUser domainUser);

    ERikshaDto updateERiksha(String id,CreateERikshaDto createERikshaDto);

    ERikshaDto getERikshaById(String id);

    void deleteERiksha(String id);

    ERiksha geteRikshabyId(String eRikshaId);

    void save(CreatePurchaseDto createPurchaseDto);

    void deleteFromERiksha(CreateSalesDto createSalesDto);
}
