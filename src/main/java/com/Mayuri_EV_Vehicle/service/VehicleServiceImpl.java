package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.entity.VechileType;
import com.Mayuri_EV_Vehicle.entity.Vehicle;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantsRepository;
import com.Mayuri_EV_Vehicle.repository.TypeRepository;
import com.Mayuri_EV_Vehicle.repository.VehicleRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService{

    private VehicleRepository vehicleRepository;
    private final EAutoVariantsRepository eAutoVariantsRepository;
    private TypeRepository typeRepository;
    private  TypeService typeService;
    private EAutoService eAutoService;

    private ERikshaService eRikshaService;

    public VehicleServiceImpl(VehicleRepository vehicleRepository,EAutoVariantsRepository eAutoVariantsRepository, TypeRepository typeRepository, TypeService typeService, @Lazy EAutoService eAutoService, @Lazy ERikshaService eRikshaService) {
        this.vehicleRepository = vehicleRepository;
        this.eAutoVariantsRepository = eAutoVariantsRepository;
        this.typeRepository = typeRepository;
        this.typeService = typeService;
        this.eAutoService = eAutoService;
        this.eRikshaService = eRikshaService;
    }

    @Override
    public List<VehicleDto> getAllVehicles(DomainUser domainUser) {
        List<Vehicle> all = vehicleRepository.findAll();
        List<VehicleDto> filteredVehicleDto=new ArrayList<>();

        if(all.size() != 0) {
            if (domainUser.getRegion_name().equals("ALL")) {
                return all.stream().map(this::convertToDto).collect(Collectors.toList());
            }

            for ( Vehicle eAutoVariantsDetails : all) {
                if (eAutoVariantsDetails.getRegion_name().equals(domainUser.getRegion_name())) {
                    filteredVehicleDto.add(convertToDto(eAutoVariantsDetails));
                }
            }
        }

        return filteredVehicleDto;
    }
    private VehicleDto convertToDto(Vehicle vehicle) {

        if(vehicle == null){
            return null;
        }

        return new VehicleDto(vehicle.getId(), vehicle.getName(), vehicle.getCompanyName(),
                vehicle.getQuantity(), vehicle.getVechileType().name(), vehicle.getRegion().name(),
                vehicle.getCreatedBy(), vehicle.getCreatedOn(), vehicle.getUpdatedBy(),
                vehicle.getUpdatedOn());
    }

    private VehicleVariantDTO convertToVehicleVariantDto( EAutoVariants eAutoVariants) {

        if(eAutoVariants == null){
            return null;
        }

        return new VehicleVariantDTO(eAutoVariants.getEAuto().getId(), eAutoVariants.getEAuto().getName(), eAutoVariants.getEAuto().getCompanyName(),
                eAutoVariants.getEAuto().getQuantity(), eAutoVariants.getEAuto().getVechileType().name(), eAutoVariants.getEAuto().getRegion().name(),
                eAutoVariants.getId(),eAutoVariants.getVariantName(),eAutoVariants.getColor(), eAutoVariants.getPrice(), eAutoVariants.getFeatures(),
                eAutoVariants.getEAuto().getCreatedBy(), eAutoVariants.getEAuto().getCreatedOn(), eAutoVariants.getEAuto().getUpdatedBy(),
                eAutoVariants.getEAuto().getUpdatedOn());
    }

    @Override
    public VehicleDto getVehicleById(String id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        return convertToDto(vehicle.get());
    }

    @Override
    public Vehicle getvehicleById(String id) {
        Optional<Vehicle> byId = vehicleRepository.findById(id);
        if(!byId.isPresent()){
            throw new RuntimeException("No vehicle found with this id");
        }

        Vehicle vehicle = byId.get();
        return vehicle;
    }

    @Override
    public VehicleDto createVehicle(CreateVehicleDTO createVehicleDTO, DomainUser domainUser) {
       Vehicle vehicle = new Vehicle(createVehicleDTO.getVariantName(), createVehicleDTO.getCompanyName(), 0, VechileType.valueOf(createVehicleDTO.getVehicleType()), Region.valueOf(domainUser.getRegion()), domainUser.getFirstName()+domainUser.getLastName(),domainUser.getRegion_name());

        return convertToDto(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleVariantDTO createVehicleVariant(CreateVehicleDTO createVehicleDTO, DomainUser domainUser) {
        VehicleDto vehicle = new VehicleDto() ;
        if(createVehicleDTO.getVehicleId() == null) {
            vehicle = createVehicle(createVehicleDTO, domainUser);
        }

        EAutoVariants eAutoVariants = new EAutoVariants(createVehicleDTO.getVariantName(),createVehicleDTO.getColor(),
                createVehicleDTO.getPrice(),createVehicleDTO.getFeatures(), Region.valueOf(domainUser.getRegion()),getvehicleById(vehicle.getVehicleId()),domainUser.getRegion_name());
        EAutoVariants save = eAutoVariantsRepository.save(eAutoVariants);

        return convertToVehicleVariantDto(save);
    }

    @Override
    public VehicleDto updateVehicle(String id, CreateVehicleDTO vehicleDTO, DomainUser domainUser) {
        Vehicle existingVehicle = getvehicleById(id);
        existingVehicle.setName(vehicleDTO.getName());
        existingVehicle.setCompanyName(vehicleDTO.getCompanyName());
        existingVehicle.setVechileType(VechileType.valueOf(vehicleDTO.getType()));
        existingVehicle.setUpdatedBy(domainUser.getFirstName()+domainUser.getLastName());
        existingVehicle.setUpdatedOn(LocalDateTime.now());
        Vehicle save = vehicleRepository.save(existingVehicle);
        return convertToDto(save);
    }

    @Override
    public void deleteVehicle(String id) {
        
        Vehicle vehicle = getvehicleById(id);
        vehicleRepository.delete(vehicle);
    }

    @Override
    public VehicleDto saveVehicle(CreatePurchaseDto createPurchaseDto) {
//        if(createPurchaseDto.getSub_categories().equals("E-Auto")){
//            eAutoService.save(createPurchaseDto);
//        }else{
//            eRikshaService.save(createPurchaseDto);
//        }
        return null;
    }

}
