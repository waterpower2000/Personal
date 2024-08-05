package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.CreateSparePartDetailsDTO;
import com.Mayuri_EV_Vehicle.dto.SparePartsVariantDetailsDTO;
import com.Mayuri_EV_Vehicle.entity.SpareParts;
import com.Mayuri_EV_Vehicle.entity.SparePartsVariantDetails;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.SparePartsVariantDetailsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SparePartsVariantDetailsServiceImpl implements SparePartsVariantDetailsService {

    private final SparePartsVariantDetailsRepository sparePartsVariantDetailsRepository;
    private final SparePartsService sparePartsService;

    public SparePartsVariantDetailsServiceImpl(SparePartsVariantDetailsRepository sparePartsVariantDetailsRepository, SparePartsService sparePartsService) {
        this.sparePartsVariantDetailsRepository = sparePartsVariantDetailsRepository;
        this.sparePartsService = sparePartsService;
    }

    @Override
    public List<SparePartsVariantDetails> getAllSpareVariants() {
        return sparePartsVariantDetailsRepository.findAll();
    }

    @Override
    public List<SparePartsVariantDetailsDTO> getAllSpareVariantDetails() {
        return getAllSpareVariants().stream().map(this::convertIntoDTO).collect(Collectors.toList());
    }

    @Override
    public SparePartsVariantDetails getSpareVariant(String id) {
        Optional<SparePartsVariantDetails> sparePartsVariantDetailsOptional = sparePartsVariantDetailsRepository.findById(id);
       if(!sparePartsVariantDetailsOptional.isPresent()){
           throw new RuntimeException("No spare part variant found with this id");
       }

        return sparePartsVariantDetailsOptional.get();
    }

    @Override
    public SparePartsVariantDetailsDTO getSpareVariantDetails(String id) {
        return convertIntoDTO(getSpareVariant(id));
    }

    @Override
    public SparePartsVariantDetailsDTO createSparePartVariant(CreateSparePartDetailsDTO createSparePartDetails, DomainUser domainUser) {

        SpareParts spareParts = sparePartsService.getSparePartsById(createSparePartDetails.getSparePartsId());
        return convertIntoDTO(sparePartsVariantDetailsRepository.save(new SparePartsVariantDetails(spareParts, createSparePartDetails.getChassisNumber(), createSparePartDetails.getModalNumber(), Region.valueOf(domainUser.getRegion()),domainUser.getRegion_name())));
    }

    @Override
    public SparePartsVariantDetailsDTO convertIntoDTO(SparePartsVariantDetails sparePartsVariantDetails){
        if(sparePartsVariantDetails == null) {
            return null;
        }

        return new SparePartsVariantDetailsDTO(sparePartsVariantDetails.getId(), sparePartsVariantDetails.getSpareParts().getId(), sparePartsVariantDetails.getSpareParts().getName(), sparePartsVariantDetails.getSpareParts().getCompanyName(), sparePartsVariantDetails.getChassis_number(), sparePartsVariantDetails.getModel_number(), sparePartsVariantDetails.getRegion().name());
    }
}
