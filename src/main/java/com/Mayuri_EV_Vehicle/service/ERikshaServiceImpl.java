package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.EAuto;
import com.Mayuri_EV_Vehicle.entity.ERiksha;
import com.Mayuri_EV_Vehicle.entity.Vehicle;
import com.Mayuri_EV_Vehicle.model.Region;
import com.Mayuri_EV_Vehicle.repository.ERikshaRepository;
import com.Mayuri_EV_Vehicle.repository.VehicleRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ERikshaServiceImpl implements ERikshaService{
    private ERikshaRepository eRikshaRepository;
    private VehicleService vehicleService;
    private ERikshaVariantService eRikshaVariantService;

    public ERikshaServiceImpl(ERikshaRepository eRikshaRepository,
                              VehicleService vehicleService,
                              @Lazy ERikshaVariantService eRikshaVariantService) {
        this.eRikshaRepository = eRikshaRepository;
        this.eRikshaVariantService=eRikshaVariantService;
        this.vehicleService=vehicleService;
    }

    @Override
    public ERikshaDto createERiksha(CreateERikshaDto createERikshaDto,DomainUser domainUser) {
        Vehicle vehicle = vehicleService.getvehicleById(createERikshaDto.getVehicle_id());
        ERiksha eRiksha=new ERiksha(UUID.randomUUID().toString(),createERikshaDto.getName(),createERikshaDto.getCompanyName()
        ,vehicle,Region.valueOf(domainUser.getRegion()),domainUser.getRegion_name());
        ERiksha save = eRikshaRepository.save(eRiksha);
        return convertToDto(save);
    }

    @Override
    public List<ERikshaDto> getAll(DomainUser domainUser) {
        List<ERiksha> all = eRikshaRepository.findAll();
        List<ERikshaDto> allRikshaDto=new ArrayList<>();
        if(domainUser.getRegion_name().equals("ALL")) {
        	for (ERiksha eRiksha : all) {
                ERikshaDto ERikshaDto = convertToDto(eRiksha);
                allRikshaDto.add(ERikshaDto);
            }
        }else {
        	for (ERiksha eRiksha : all) {
        		if(eRiksha.getRegion_name().equals(domainUser.getRegion_name())) {
        			ERikshaDto ERikshaDto = convertToDto(eRiksha);
                    allRikshaDto.add(ERikshaDto);
        		}                
            }
        }       
        return allRikshaDto;
    }

    @Override
    public ERikshaDto updateERiksha(String id,CreateERikshaDto createERikshaDto) {
        Optional<ERiksha> byId = eRikshaRepository.findById(id);
        ERiksha eRiksha =byId.get();
        eRiksha.setName(createERikshaDto.getName());
        eRiksha.setCompanyName(createERikshaDto.getCompanyName());
        ERiksha save = eRikshaRepository.save(eRiksha);
        return convertToDto(save);
    }

    @Override
    public ERikshaDto getERikshaById(String id) {
        Optional<ERiksha> byId = eRikshaRepository.findById(id);
        return convertToDto(byId.get());
    }

    @Override
    public void deleteERiksha(String id) {
        eRikshaRepository.deleteById(id);
    }

    @Override
    public ERiksha geteRikshabyId(String eRikshaId) {
        Optional<ERiksha> byId = eRikshaRepository.findById(eRikshaId);
        ERiksha eRiksha =byId.get();
        return eRiksha;
    }

    @Override
    public void save(CreatePurchaseDto createPurchaseDto) {
//        int flag=0;
//        List<ERiksha> all = eRikshaRepository.findAll();
//        for (ERiksha eRiksha : all) {
//            if(eRiksha.getName().equals(createPurchaseDto.getName()) && flag ==0){
//                eRiksha.setQuantity(eRiksha.getQuantity()+1);
//                eRiksha.setCompanyName(createPurchaseDto.getVariants());
//                ERiksha save1 = eRikshaRepository.save(eRiksha);
//                eRikshaVariantService.save(save1,createPurchaseDto);
//                flag ++;
//            }
//        }
//        if (flag == 0 ) {
//            ERiksha eRiksha=new ERiksha();
//            eRiksha.setQuantity(1);
//            eRiksha.setName(createPurchaseDto.getName());
//            eRiksha.setCompanyName(createPurchaseDto.getVariants());
//            ERiksha save = eRikshaRepository.save(eRiksha);
//            eRikshaVariantService.save(save, createPurchaseDto);
//        }

    }

    @Override
    public void deleteFromERiksha(CreateSalesDto createSalesDto) {
//        int flag=0;
//        List<ERiksha> all = eRikshaRepository.findAll();
//        for (ERiksha eRiksha : all) {
//            if(eRiksha.getName().equals(createSalesDto.getSales_name()) && flag ==0){
//            	if(eRiksha.getQuantity()>0){
//            		eRiksha.setQuantity(eRiksha.getQuantity()-1);
//            		ERiksha save = eRikshaRepository.save((eRiksha));
//            		eRikshaVariantService.delete(save,createSalesDto);
//            		flag ++;
//            	}else {
//            		System.out.println("Error");
//            	}
//            }
//        }
    }

    private ERikshaDto convertToDto(ERiksha eRiksha) {
        ERikshaDto erikshaDto=new ERikshaDto();
        erikshaDto.setId(eRiksha.getId());
        erikshaDto.setName(eRiksha.getName());
        erikshaDto.setCompanyName(eRiksha.getCompanyName());
        return erikshaDto;
    }

}
