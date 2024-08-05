package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.CreateERikshaVariantsDto;
import com.Mayuri_EV_Vehicle.dto.ERikshaVariantsDto;
import com.Mayuri_EV_Vehicle.service.ERikshaVariantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/ERikshavariantDetails")
public class ERikshaVariantsController {
    private ERikshaVariantService eRikshaVariantService;

    public ERikshaVariantsController(ERikshaVariantService eRikshaVariantService) {
        this.eRikshaVariantService = eRikshaVariantService;
    }
    @PostMapping
    public ModelAndView createERikshaVariant(@RequestBody CreateERikshaVariantsDto createERikshaVariantsDto){
    	ModelAndView modelAndView=new ModelAndView();
        ERikshaVariantsDto variants = eRikshaVariantService.createVariants(createERikshaVariantsDto);
        return modelAndView;
    }
    @GetMapping("/getAllERiksha")
    public ModelAndView getAllVariants(){
    	ModelAndView modelAndView=new ModelAndView();
        List<ERikshaVariantsDto> allVariants = eRikshaVariantService.getAllVariants();
        return modelAndView;
    }
    @GetMapping("/variants/{variant_id}")
    public ModelAndView getById(@PathVariable String variant_id){
    	ModelAndView modelAndView=new ModelAndView();
        ERikshaVariantsDto variantById = eRikshaVariantService.getVariantById(variant_id);
        return modelAndView;
    }
    @DeleteMapping("/deletevariants/{variant_id}")
    public ModelAndView deleteVariantDetails(@PathVariable String variant_id){
    	ModelAndView modelAndView=new ModelAndView();
        eRikshaVariantService.deleteById(variant_id);
        return modelAndView;
    }
    @PutMapping("/{variant_id}")
    public ModelAndView update(@PathVariable String variant_id, @RequestBody CreateERikshaVariantsDto createERikshaVariantsDto){
    	ModelAndView modelAndView=new ModelAndView();
    	ERikshaVariantsDto eRikshaVariantsDto = eRikshaVariantService.updateVariants(variant_id, createERikshaVariantsDto);
        return modelAndView;
    }
}
