package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateSparePartsDTO;
import com.Mayuri_EV_Vehicle.dto.SparePartsDTO;
import com.Mayuri_EV_Vehicle.entity.SpareParts;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.SparePartsRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SparePartsServiceImpl implements SparePartsService {

    private final SparePartsRepository sparePartsRepository;

    public SparePartsServiceImpl(SparePartsRepository sparePartsRepository) {
        this.sparePartsRepository = sparePartsRepository;
    }

    @Override
    public List<SpareParts> getAllSpareParts() {
        return sparePartsRepository.findAll();
    }

    @Override
    public List<SparePartsDTO> getAllSparePartsDetails() {
        return getAllSpareParts().stream().map(this::convertIntoDTO).collect(Collectors.toList());
    }

    @Override
    public List<SparePartsDTO> getAllSparePartsByRegion(DomainUser domainUser) {
        List<SpareParts> all = sparePartsRepository.findAll();
        List<SparePartsDTO> filteredSparePartsDto = new ArrayList<>();

        if (all.size() != 0) {
            if (domainUser.getRegion_name().equals("ALL")) {
                return all.stream().map(this::convertIntoDTO).collect(Collectors.toList());
            }

            for (SpareParts spareParts : all) {
                if (spareParts.getRegion_name().equals(domainUser.getRegion_name())) {
                    filteredSparePartsDto.add(convertIntoDTO(spareParts));
                }
            }
        }

        return filteredSparePartsDto;
    }

    @Override
    public SpareParts getSparePartsById(String id) {

        Optional<SpareParts> sparePartsOptional = sparePartsRepository.findById(id);
        if(!sparePartsOptional.isPresent()){
            throw new RuntimeException("No Spare parts found with this id");
        }
        return sparePartsOptional.get();
    }

    @Override
    public SparePartsDTO getSparePartsIdDetails(String id) {
        return convertIntoDTO(getSparePartsById(id));
    }

    @Override
    public SparePartsDTO createSpareParts(CreateSparePartsDTO createSpareParts, DomainUser domainUser) {
        SpareParts spareParts = new SpareParts(createSpareParts.getName(), createSpareParts.getCompanyName(), Region.valueOf(domainUser.getRegion()), domainUser.getFirstName()+ " "+domainUser.getLastName(),domainUser.getRegion_name());
        return convertIntoDTO(sparePartsRepository.save(spareParts));
    }

    @Override
    public SparePartsDTO updateSpareParts(String id, CreateSparePartsDTO createSpareParts, DomainUser domainUser) {
        return null;
    }

    @Override
    public void deleteSpareParts(String id) {

    }

    @Override
    public SparePartsDTO convertIntoDTO(SpareParts spareParts) {

        if(spareParts == null) {
            return null;
        }

        SparePartsDTO sparePartsDTO = new SparePartsDTO(spareParts.getId(),
                spareParts.getName(),
                spareParts.getCompanyName(),
                spareParts.getRegion().name(),
                spareParts.getQuantity(),
                spareParts.getCreatedBy(),
                spareParts.getCreatedOn(),
                spareParts.getUpdatedBy(),
                spareParts.getUpdatedOn());

        return sparePartsDTO;

    }
}
