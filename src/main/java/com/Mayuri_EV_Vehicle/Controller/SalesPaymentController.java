package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.SalesPaymentDto;
import com.Mayuri_EV_Vehicle.service.SalesPaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/salespayment")
public class SalesPaymentController {
    private SalesPaymentService salesPaymentService;

    public SalesPaymentController(SalesPaymentService salesPaymentService) {
        this.salesPaymentService = salesPaymentService;
    }
    @PostMapping
    public ModelAndView save(@RequestBody CreateSalesDto createSalesDto){
    	ModelAndView modelAndView=new ModelAndView();
        SalesPaymentDto salesPaymentDto = salesPaymentService.create(createSalesDto);
        return modelAndView;
    }
}
