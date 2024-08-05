package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.SupplierDto;
import com.Mayuri_EV_Vehicle.service.SupplierServcie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {
    private SupplierServcie supplierServcie;

    public SupplierController(SupplierServcie supplierServcie) {
        this.supplierServcie = supplierServcie;
    }
    @PostMapping
    public ModelAndView create(@RequestBody SupplierDto supplierDto){
    	ModelAndView modelAndView=new ModelAndView();
        SupplierDto save = supplierServcie.save(supplierDto);
        return modelAndView;
    }
    @PostMapping("/")
    public ModelAndView createSupplier(@RequestBody CreatePurchaseDto createPurchaseDto){
    	ModelAndView modelAndView=new ModelAndView();
        SupplierDto save = supplierServcie.save(createPurchaseDto);
        return modelAndView;
    }
    @GetMapping
    public ModelAndView getAll(){
    	ModelAndView modelAndView=new ModelAndView();
        List<SupplierDto> all = supplierServcie.getAll();
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView getSupplierById(@PathVariable String id){
    	ModelAndView modelAndView=new ModelAndView();
        SupplierDto byId = supplierServcie.getById(id);
        return modelAndView;
    }

}
