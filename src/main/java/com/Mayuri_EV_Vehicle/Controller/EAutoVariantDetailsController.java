package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateEAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantsDto;
import com.Mayuri_EV_Vehicle.service.EAutoVariantDetailsService;
import org.hibernate.cache.spi.support.DomainDataStorageAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/EAutoVariantDetails")
public class EAutoVariantDetailsController {

    private EAutoVariantDetailsService eAutoVariantDetailsService;

    public EAutoVariantDetailsController(EAutoVariantDetailsService eAutoVariantDetailsService) {
        this.eAutoVariantDetailsService = eAutoVariantDetailsService;
    }

    @PostMapping
    public ModelAndView createVariantDetails(@RequestBody CreateEAutoVariantDetailsDto createEAutoVariantDetailsDto, @AuthenticationPrincipal DomainUser domainUser) {
    	ModelAndView modelAndView=new ModelAndView();
    	EAutoVariantDetailsDto variantDetail = eAutoVariantDetailsService.createVariantDetail(createEAutoVariantDetailsDto, domainUser);
        return modelAndView;
    }

    @GetMapping("/getAll")
    public ModelAndView getAllVariants(@AuthenticationPrincipal DomainUser domainUser) {
    	ModelAndView modelAndView=new ModelAndView();
    	List<EAutoVariantDetailsDto> allVariants = eAutoVariantDetailsService.getAllVariants(domainUser);
        return modelAndView;
    }

    @GetMapping("/{variantDetails_id}")
    public ModelAndView getVariantsDetailsById(@PathVariable String variantDetails_id) {
    	ModelAndView modelAndView=new ModelAndView();
    	EAutoVariantDetailsDto variantsDetailsById = eAutoVariantDetailsService.getVariantsDetailsById(variantDetails_id);
        return modelAndView;
    }

    @PutMapping("/update/{variantDetails_id}")
    public ModelAndView updateVariantDetails(
            @PathVariable String variantDetails_id,
            @RequestBody CreateEAutoVariantDetailsDto createEAutoVariantDetailsDto) {
    	ModelAndView modelAndView=new ModelAndView();
    	EAutoVariantDetailsDto eAutoVariantDetailsDto = eAutoVariantDetailsService.upadteVariantDetails(variantDetails_id, createEAutoVariantDetailsDto);
        return modelAndView;
    }
    @DeleteMapping("/delete/{variantDetails_id}")
    public ModelAndView deleteVariantDetails(@PathVariable String variantDetails_id){
    	ModelAndView modelAndView=new ModelAndView();
    	eAutoVariantDetailsService.deleteVariantDetails(variantDetails_id);
        return modelAndView;
    }
}
