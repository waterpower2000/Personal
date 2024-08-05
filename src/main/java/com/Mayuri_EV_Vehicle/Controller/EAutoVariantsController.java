package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoVariantsDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantsDto;
import com.Mayuri_EV_Vehicle.service.EAutoVariantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/eAuto/variantDetails")
public class EAutoVariantsController {

    private EAutoVariantsService eAutoVariantsService;

    public EAutoVariantsController(EAutoVariantsService eAutoVariantsService) {
        this.eAutoVariantsService = eAutoVariantsService;
    }
    @PostMapping
    public ModelAndView createEAutoVariant(@RequestBody CreateEAutoVariantsDto createEAutoVariantsDto){
    	ModelAndView modelAndView=new ModelAndView();
        EAutoVariantsDto variants = eAutoVariantsService.createVariants(createEAutoVariantsDto);
        return modelAndView;
    }
    @GetMapping("/getAll")
    public ModelAndView getAllVariants(@AuthenticationPrincipal DomainUser domainUser){
    	 ModelAndView modelAndView=new ModelAndView();
         List<EAutoVariantsDto> allVariants = eAutoVariantsService.getAllVariants(domainUser);
         return modelAndView;
    }
    @GetMapping("/getAll/{auto_id}")
    public ModelAndView getAllVariants(@PathVariable String auto_id){
    	ModelAndView modelAndView=new ModelAndView();
        List<EAutoVariantsDto> allVariantsById = eAutoVariantsService.getAllVariantsById(auto_id);
        return modelAndView;
    }
    @GetMapping("/variants/{variant_id}")
    public ModelAndView getById(@PathVariable String variant_id){
    	ModelAndView modelAndView=new ModelAndView();
        EAutoVariantsDto variantById = eAutoVariantsService.getVariantById(variant_id);
        return modelAndView;
    }
    @DeleteMapping("/deletevariants/{variant_id}")
    public ModelAndView deleteVariantDetails(@PathVariable String variant_id){
    	ModelAndView modelAndView=new ModelAndView();
        eAutoVariantsService.deleteById(variant_id);
        return modelAndView;
    }
    @PutMapping("/{variant_id}")
    public ModelAndView update(@PathVariable String variant_id, @RequestBody CreateEAutoVariantsDto createEAutoVariantsDto){
    	ModelAndView modelAndView=new ModelAndView();
    	EAutoVariantsDto variants = eAutoVariantsService.updateVariants(variant_id, createEAutoVariantsDto);
        return modelAndView;
    }
}
