package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoDto;
import com.Mayuri_EV_Vehicle.dto.EAutoDto;
import com.Mayuri_EV_Vehicle.service.EAutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/api/eauto")
public class EAutoController {
    private EAutoService eAutoService;

    public EAutoController(EAutoService eAutoService) {
        this.eAutoService = eAutoService;
    }

    @PostMapping
    public ModelAndView saveEAuto(@RequestBody CreateEAutoDto createEAutoDto,@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView=new ModelAndView();
        EAutoDto eAuto = eAutoService.createEAuto(createEAutoDto,domainUser);
        return modelAndView;
    }
    @PostMapping("/getAllByVehicleId")
    public ModelAndView getAllEAutoByVehicleId(@RequestBody CreateEAutoDto createEAutoDto){
    	ModelAndView modelAndView=new ModelAndView();
        List<EAutoDto> all =eAutoService.getByVehicleId(createEAutoDto);
        return modelAndView;
    }
    @GetMapping("/geteauto")
    public ModelAndView getallEAuto(@AuthenticationPrincipal DomainUser domainUser){
        ModelAndView modelAndView=new ModelAndView();
        List<EAutoDto> all =eAutoService.getAll(domainUser);
        modelAndView.addObject("allEAuto", all);
        modelAndView.addObject("message", "All E-Auto List");
        modelAndView.setViewName("redirect:/api/eauto/getall");
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView getEAutoById(@PathVariable String id){
    	ModelAndView modelAndView=new ModelAndView();
        EAutoDto eAutoById = eAutoService.getEAutoById(id);
        return modelAndView;
    }
    @PutMapping("/{id}")
    public ModelAndView updateEAuto(@PathVariable String id,@RequestBody CreateEAutoDto createEAutoDto) {
    	ModelAndView modelAndView=new ModelAndView();
        EAutoDto updatedEAutoDTO = eAutoService.updateERiksha(id,createEAutoDto);
        return modelAndView;
    }
    @DeleteMapping("/{id}")
    public ModelAndView deleteEAuto(@PathVariable String id) {
    	ModelAndView modelAndView=new ModelAndView();
        eAutoService.deleteEAuto(id);
        return modelAndView;
    }
    }
