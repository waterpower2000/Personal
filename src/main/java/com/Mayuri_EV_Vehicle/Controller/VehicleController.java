package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.service.EAutoVariantDetailsService;
import com.Mayuri_EV_Vehicle.service.SparePartsService;
import com.Mayuri_EV_Vehicle.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/api/vehicle")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private EAutoVariantDetailsService eAutoVariantDetailsService;

    @Autowired
    private SparePartsService sparePartsService;
    
    @GetMapping
    public ModelAndView getAllVehicles(@AuthenticationPrincipal DomainUser domainUser) {
    	ModelAndView modelAndView=new ModelAndView();
        List<VehicleDto> allVehicles = vehicleService.getAllVehicles(domainUser);
        return modelAndView;
    }

    @GetMapping("/vehicles/{id}")
    public ModelAndView getVehicleById(@PathVariable String id) {
    	ModelAndView modelAndView=new ModelAndView();
        VehicleDto vehicleById = vehicleService.getVehicleById(id);
        return modelAndView;
    }

    @GetMapping("/add/vehicle")
    public ModelAndView addVehiclePage(@AuthenticationPrincipal DomainUser domainUser) {
        ModelAndView modelAndView = new ModelAndView();
       List<VehicleDto> vehicleDtos = vehicleService.getAllVehicles(domainUser);
       modelAndView.addObject("vehicles", vehicleDtos);
        modelAndView.addObject("createVehicle", new CreateVehicleDTO());
        String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
        modelAndView.setViewName("add-vehicle");
        return modelAndView;
    }

    @PostMapping("/create/vehicle")
    public ModelAndView createVehicle(@ModelAttribute("createVehicle") CreateVehicleDTO createVehicleDTO, @AuthenticationPrincipal DomainUser domainUser, HttpSession session) {
    	ModelAndView modelAndView=new ModelAndView();

        if(createVehicleDTO.getType().equals("SPARE_PARTS")){
            CreateSparePartsDTO createSparePartsDTO = new CreateSparePartsDTO(createVehicleDTO.getName(), createVehicleDTO.getCompanyName());
            sparePartsService.createSpareParts(createSparePartsDTO, domainUser);
            session.setAttribute("message", new MessageDTO("Spare Parts added Successfully", "Success"));
            modelAndView.addObject("message", "Spare Parts added Successfully");
        }
        else {
            if(createVehicleDTO.getVehicleId().equals("null")){
                createVehicleDTO.setVehicleId(null) ;
            }
            vehicleService.createVehicleVariant(createVehicleDTO, domainUser);
            session.setAttribute("message", new MessageDTO("Vehicle added Successfully", "Success"));
            modelAndView.addObject("message", "Vehicle added Successfully");
        }

        modelAndView.setViewName("redirect:/api/vehicle/add/vehicle");
        return modelAndView;
    }

    @GetMapping("/edit/vehicle/{id}")
    public ModelAndView editVehicle(@PathVariable String id){
        ModelAndView modelAndView=new ModelAndView();
        EAutoVariantDetails eAutoVariantDetails = eAutoVariantDetailsService.getVariantsDetails(id);
        VehicleDto vehicleDto = vehicleService.getVehicleById(eAutoVariantDetails.getEAutoVariants().getEAuto().getId());
        modelAndView.addObject("vehicle", vehicleDto);
        modelAndView.addObject("createVehicle", new CreateVehicleDTO());
        modelAndView.setViewName("edit-vehicle");
        return modelAndView;

    }

    @PostMapping("/vehicle/update/{id}")
    public ModelAndView updateVehicle(@PathVariable String id, @RequestBody CreateVehicleDTO vehicleDTO, DomainUser domainUser)  {
    	ModelAndView modelAndView=new ModelAndView();
        VehicleDto vehicleDto = vehicleService.updateVehicle(id, vehicleDTO, domainUser);
        modelAndView.setViewName("redirect:/api/vehicle/add/vehicle");
        return modelAndView;
    }

    @DeleteMapping("/deletevehicles/{id}")
    public ModelAndView deleteVehicle(@PathVariable String id) throws Throwable {
    	ModelAndView modelAndView=new ModelAndView();
        vehicleService.deleteVehicle(id);
        return modelAndView;
    }

}
