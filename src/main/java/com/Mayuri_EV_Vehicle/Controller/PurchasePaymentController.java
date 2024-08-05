package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.PurchasePaymentDto;
import com.Mayuri_EV_Vehicle.service.PurchasePayementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/purchaseprice")
public class PurchasePaymentController {
    private PurchasePayementService purchasePayementService;

    public PurchasePaymentController(PurchasePayementService purchasePayementService) {
        this.purchasePayementService = purchasePayementService;
    }
    @PostMapping
    public ModelAndView createPayment(@RequestBody CreatePurchaseDto createPurchaseDto){
    	ModelAndView modelAndView=new ModelAndView();
        PurchasePaymentDto save = purchasePayementService.save(createPurchaseDto);
        return modelAndView;
    }
}
