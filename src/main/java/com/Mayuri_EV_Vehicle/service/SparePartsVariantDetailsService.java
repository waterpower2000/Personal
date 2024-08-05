package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.CreateSparePartDetailsDTO;
import com.Mayuri_EV_Vehicle.dto.SparePartsVariantDetailsDTO;
import com.Mayuri_EV_Vehicle.entity.SparePartsVariantDetails;

import java.util.List;

public interface SparePartsVariantDetailsService {
    List<SparePartsVariantDetails> getAllSpareVariants();
    List<SparePartsVariantDetailsDTO> getAllSpareVariantDetails();

    SparePartsVariantDetails getSpareVariant(String id);
    SparePartsVariantDetailsDTO getSpareVariantDetails(String id);
    SparePartsVariantDetailsDTO createSparePartVariant(CreateSparePartDetailsDTO createSparePartDetails, DomainUser domainUser);

    SparePartsVariantDetailsDTO convertIntoDTO(SparePartsVariantDetails sparePartsVariantDetails);
}
