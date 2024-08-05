package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateSparePartsDTO;
import com.Mayuri_EV_Vehicle.dto.SparePartsDTO;
import com.Mayuri_EV_Vehicle.entity.SpareParts;

import java.util.List;

public interface SparePartsService {

    List<SpareParts> getAllSpareParts();

    List<SparePartsDTO> getAllSparePartsDetails();

    List<SparePartsDTO> getAllSparePartsByRegion(DomainUser domainUser);

    SpareParts getSparePartsById(String id);

    SparePartsDTO getSparePartsIdDetails(String id);
    SparePartsDTO createSpareParts(CreateSparePartsDTO createSpareParts, DomainUser domainUser);

    SparePartsDTO updateSpareParts(String id, CreateSparePartsDTO createSpareParts, DomainUser domainUser);

    void deleteSpareParts(String id);

    SparePartsDTO convertIntoDTO(SpareParts spareParts);


}
