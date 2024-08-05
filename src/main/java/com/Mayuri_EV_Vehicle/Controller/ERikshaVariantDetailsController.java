package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.CreateEAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.CreateERikshaVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.ERikshaVariantDetailsDto;
import com.Mayuri_EV_Vehicle.service.EAutoVariantDetailsService;
import com.Mayuri_EV_Vehicle.service.ERikshaVariantDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/erikshavariant/details")
public class ERikshaVariantDetailsController {
    private ERikshaVariantDetailsService eRikshaVariantDetailsService;

    public ERikshaVariantDetailsController(ERikshaVariantDetailsService eRikshaVariantDetailsService) {
        this.eRikshaVariantDetailsService = eRikshaVariantDetailsService;
    }

    @PostMapping
    public ModelAndView createVariantDetails(@RequestBody CreateERikshaVariantDetailsDto createERikshaVariantDetailsDto) {
    	ModelAndView modelAndView=new ModelAndView();
    	ERikshaVariantDetailsDto variantDetail = eRikshaVariantDetailsService.createVariantDetail(createERikshaVariantDetailsDto);
        return modelAndView;
    }

    @GetMapping("/getAll")
    public ModelAndView getAllVariants() {
    	ModelAndView modelAndView=new ModelAndView();
    	List<ERikshaVariantDetailsDto> allVariants = eRikshaVariantDetailsService.getAllVariants();
        return modelAndView;
    }

    @GetMapping("/{variantDetails_id}")
    public ModelAndView getVariantsDetailsById(@PathVariable String variantDetails_id) {
    	ModelAndView modelAndView=new ModelAndView();
    	ERikshaVariantDetailsDto variantsDetailsById = eRikshaVariantDetailsService.getVariantsDetailsById(variantDetails_id);
        return modelAndView;
    }
    @PutMapping("/update/{variantDetails_id}")
    public ModelAndView updateVariantDetails(
            @PathVariable String variantDetails_id,
            @RequestBody CreateERikshaVariantDetailsDto createERikshaVariantDetailsDto) {
    	ModelAndView modelAndView=new ModelAndView();
    	ERikshaVariantDetailsDto eRikshaVariantDetailsDto = eRikshaVariantDetailsService.upadteVariantDetails(variantDetails_id, createERikshaVariantDetailsDto);
        return modelAndView;
    }
    @DeleteMapping("/delete/{variantDetails_id}")
    public ModelAndView deleteVariantDetails(@PathVariable String variantDetails_id){
    	ModelAndView modelAndView=new ModelAndView();
    	eRikshaVariantDetailsService.deleteVariantDetails(variantDetails_id);
        return modelAndView;
    }
}
