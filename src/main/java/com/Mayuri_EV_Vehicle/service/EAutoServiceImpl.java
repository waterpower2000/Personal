package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.EAuto;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.entity.Type;
import com.Mayuri_EV_Vehicle.entity.Vehicle;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.EAutoRepository;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantsRepository;
import com.Mayuri_EV_Vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EAutoServiceImpl implements EAutoService{
    private EAutoRepository eAutoRepository;
    private VehicleService vehicleService;
    private EAutoVariantsService eAutoVariantsService;
    private EAutoVariantDetailsService eAutoVariantDetailsService;

    public EAutoServiceImpl(EAutoRepository eAutoRepository,
                            VehicleService vehicleService,
                            @Lazy EAutoVariantsService eAutoVariantsService,
                            @Lazy EAutoVariantDetailsService eAutoVariantDetailsService) {
        this.eAutoRepository = eAutoRepository;
        this.vehicleService = vehicleService;
        this.eAutoVariantsService=eAutoVariantsService;
        this.eAutoVariantDetailsService=eAutoVariantDetailsService;
    }
    @Override
    public EAutoDto createEAuto(CreateEAutoDto createEAutoDto,DomainUser domainUser) {
        Vehicle vehicle = vehicleService.getvehicleById(createEAutoDto.getVehicle_id());
        EAuto eAuto=new EAuto(UUID.randomUUID().toString(),createEAutoDto.getName(),createEAutoDto.getCompanyName(),
        		vehicle, Region.valueOf(domainUser.getRegion()),domainUser.getRegion_name());
        EAuto save = eAutoRepository.save(eAuto);
        return convertToDto(save);

    }
    @Override
    public List<EAutoDto> getAll(DomainUser domainUser) {
        List<EAuto> all = eAutoRepository.findAll();
        List<EAutoDto> allVehicleDto=new ArrayList<>();
        if(domainUser.getRegion_name().equals("ALL")) {
        	for (EAuto eAuto : all) {
        		EAutoDto EAutoDto = convertToDto(eAuto);
        		allVehicleDto.add(EAutoDto);
        	}
        }else {
        	for (EAuto eAuto : all) {
        		if(eAuto.getRegion_name().equals(domainUser.getRegion_name())) {
        			EAutoDto EAutoDto = convertToDto(eAuto);
            		allVehicleDto.add(EAutoDto);
        		}
        	}
        }
        return allVehicleDto;
    }
    private EAutoDto convertToDto(EAuto eAuto) {
        EAutoDto eAutoDto=new EAutoDto();
        eAutoDto.setId(eAuto.getId());
        eAutoDto.setVehicle_id(eAutoDto.getVehicle_id());
        eAutoDto.setName(eAuto.getName());
        eAutoDto.setCompanyName(eAuto.getCompanyName());
        return eAutoDto;
    }
    @Override
    public EAutoDto getEAutoById(String id) {
        Optional<EAuto> byId = eAutoRepository.findById(id);
        EAuto eAuto = byId.get();
        return convertToDto(eAuto);
    }

    @Override
    public EAuto getAutoByAutoId(String id) {
        Optional<EAuto> byId = eAutoRepository.findById(id);
        EAuto eAuto = byId.get();
        List<EAutoVariantsDto> allVariantsById = eAutoVariantsService.getAllVariantsById(eAuto.getId());
        return eAuto;
    }

    @Override
    public EAutoDto updateERiksha(String id, CreateEAutoDto createEAutoDto) {
        Optional<EAuto> byId = eAutoRepository.findById(id);
        EAuto eAuto = byId.get();
        eAuto.setName(createEAutoDto.getName());
        eAuto.setCompanyName(createEAutoDto.getCompanyName());
        EAuto save = eAutoRepository.save(eAuto);
        return convertToDto(save);
    }
    @Override
    public void deleteEAuto(String id) {
        eAutoRepository.deleteById(id);
    }

    @Override
    public List<EAutoDto> getByVehicleId(CreateEAutoDto createEAutoDto) {
        Vehicle vehicle = vehicleService.getvehicleById(createEAutoDto.getVehicle_id());
        Optional<List<EAuto>> byVehicleId = eAutoRepository.findByVehicleId(vehicle.getId());
        List<EAuto> eAutos = byVehicleId.get();
        List<EAutoDto> allVehicleDto=new ArrayList<>();

        for (EAuto eAuto : eAutos) {
            EAutoDto EAutoDto = convertToDto(eAuto);
            allVehicleDto.add(EAutoDto);
        }
        return allVehicleDto;
    }

    @Override
    public void save(CreatePurchaseDto createPurchaseDto) {
//        int flag=0;
//        List<EAuto> all = eAutoRepository.findAll();
//        for (EAuto eAuto : all) {
//            if(eAuto.getName().equals(createPurchaseDto.getName()) && flag ==0){
//                eAuto.setQuantity(eAuto.getQuantity()+1);
//                eAuto.setCompanyName(createPurchaseDto.getVariants());
////                eAuto.setRegion(Region.valueOf(domainUser.getRegion()));
//                EAuto save = eAutoRepository.save((eAuto));
//                eAutoVariantsService.save(save,createPurchaseDto);
//                flag ++;
//            }
//        }
//        if (flag == 0 ) {
//            EAuto eAuto=new EAuto();
//            eAuto.setQuantity(1);
//            eAuto.setName(createPurchaseDto.getName());
//            eAuto.setCompanyName(createPurchaseDto.getVariants());
//            EAuto savedEAuto = eAutoRepository.save(eAuto);
//            eAutoVariantsService.save(savedEAuto, createPurchaseDto);
//        }
    }

    @Override
    public void deleteFromEAuto(CreateSalesDto createSalesDto) {
//        int flag=0;
//        List<EAuto> all = eAutoRepository.findAll();
//        for (EAuto eAuto : all) {
//            if(eAuto.getName().equals(createSalesDto.getSales_name()) && flag ==0){
//            	if(eAuto.getQuantity()>0){
//            		eAuto.setQuantity(eAuto.getQuantity()-1);
//            		EAuto save = eAutoRepository.save((eAuto));
//            		eAutoVariantsService.delete(save,createSalesDto);
//            		flag ++;
//            	}else {
//            		System.out.println("Error");
//            	}
//            }
//        }
//    }
}}
