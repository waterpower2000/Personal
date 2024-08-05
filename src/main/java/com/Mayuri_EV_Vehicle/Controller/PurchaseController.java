package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.*;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import com.Mayuri_EV_Vehicle.entity.Purchase;
import com.Mayuri_EV_Vehicle.entity.SpareParts;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantDetailsRepository;
import com.Mayuri_EV_Vehicle.repository.EAutoVariantsRepository;
import com.Mayuri_EV_Vehicle.service.EAutoVariantDetailsService;
import com.Mayuri_EV_Vehicle.service.EAutoVariantsService;
import com.Mayuri_EV_Vehicle.service.PurchaseService;
import com.Mayuri_EV_Vehicle.service.SparePartsService;
import com.Mayuri_EV_Vehicle.service.SparePartsVariantDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/purchase")
public class PurchaseController {
    private PurchaseService purchaseService;

    private final EAutoVariantsService eAutoVariantsService;

    private final SparePartsService sparePartsService;
    private final EAutoVariantDetailsRepository eAutoVariantDetailsRepository;
    private final EAutoVariantDetailsService eAutoVariantDetailsService;

    public PurchaseController(PurchaseService purchaseService, EAutoVariantsService eAutoVariantsService, SparePartsService sparePartsService, EAutoVariantDetailsRepository eAutoVariantDetailsRepository
    		,EAutoVariantDetailsService eAutoVariantDetailsService) {
        this.purchaseService = purchaseService;
        this.eAutoVariantsService = eAutoVariantsService;
        this.sparePartsService = sparePartsService;
        this.eAutoVariantDetailsRepository = eAutoVariantDetailsRepository;
        this.eAutoVariantDetailsService = eAutoVariantDetailsService;
    }

    @PostMapping("/")
    public ModelAndView savePuchase(@ModelAttribute("purchase") CreatePurchaseDto createPurchaseDto, @AuthenticationPrincipal DomainUser domainUser,HttpSession session){
    	 ModelAndView modelAndView=new ModelAndView();
         PurchaseDto purchaseDto = purchaseService.saveOnePurchase(createPurchaseDto,domainUser);
         session.setAttribute("message", new MessageDTO("Purchase Done Successfully", "Success"));
         modelAndView.addObject("message", "Purchase Done Successfully");
         modelAndView.setViewName("redirect:/api/home/purchase");
         return modelAndView;
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView saveUpdatedPuchase(@ModelAttribute("details") EAutoVariantDetails eAutoVariantDetails, @AuthenticationPrincipal DomainUser domainUser,HttpSession session){
    	EAutoVariantDetails eAutoVariantDetails2 = eAutoVariantDetailsService.getVariantsDetails(eAutoVariantDetails.getId());
    	eAutoVariantDetails2.setChassis_number(eAutoVariantDetails.getChassis_number());
    	eAutoVariantDetails2.setMoter_number(eAutoVariantDetails.getMoter_number());
    	eAutoVariantDetails2.setOnRoadPrice(eAutoVariantDetails.getOnRoadPrice());
    	eAutoVariantDetailsRepository.save(eAutoVariantDetails2);
    	ModelAndView modelAndView=new ModelAndView();
         session.setAttribute("message", new MessageDTO("Purchase Edited Successfully", "Success"));
         modelAndView.addObject("message", "Purchase Done Successfully");
         modelAndView.addObject("purchase", new CreatePurchaseDto());
         String reg;
 		reg=domainUser.getRegion_name();
 		modelAndView.addObject("reg", reg);
 		modelAndView.addObject("variants", eAutoVariantsService.getAllVariants(domainUser));
         modelAndView.setViewName("purchase");
         return modelAndView;
    }
    
    @GetMapping
    public ResponseEntity<List<PurchaseDto>> getAllPurchase(){
        List<PurchaseDto> all = purchaseService.getAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }
    @GetMapping("/getAllPurchase")
    public ModelAndView getAllPurchaseEntity(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView=new ModelAndView();
        List<Purchase> allPurchase = purchaseService.getAllPurchase(domainUser);
        modelAndView.addObject("allPurchase", allPurchase);
        modelAndView.setViewName(null);
        return modelAndView;
    }

    @GetMapping("/get/type")
    public ResponseEntity<List<String>> getTypes(@RequestParam String details) {
        List<String> vehicle_parts = new ArrayList<>();
        List<String> spare_parts = new ArrayList<>();
        List<String> types = new ArrayList<>();

        types.removeAll(types);

        vehicle_parts.add("Vehicle_one");
        vehicle_parts.add("Vehicle_two");
        vehicle_parts.add("Vehicle_three");
        vehicle_parts.add("Vehicle_four");

        spare_parts.add("spare_parts_one");
        spare_parts.add("spare_parts_two");
        spare_parts.add("spare_parts_three");
        spare_parts.add("spare_parts_four");
        if(details.equals("Vehicle")){
            types = vehicle_parts;
        }
        else{
            types = spare_parts;
        }
        return ResponseEntity.ok(types);
    }

    @GetMapping("get/vehicle/variants")
    public ResponseEntity<List<EAutoVariantsDto>> getAllVehicleVariants(@AuthenticationPrincipal DomainUser domainUser){
        List<EAutoVariantsDto> eAutoVariantsDtoList = eAutoVariantsService.getAllVariants(domainUser);
       // List<EAutoVariantsDto> eAutoVariantsDtoList = eAutoVariantsService.getAllfilterVariants(domainUser);
        return ResponseEntity.ok(eAutoVariantsDtoList);
    }
    @GetMapping("get/spare/parts/variants")
    public ResponseEntity<List<SparePartsDTO>> getAllSparePartVariants(@AuthenticationPrincipal DomainUser domainUser){
        List<SparePartsDTO> sparePartsDTOS = sparePartsService.getAllSparePartsByRegion(domainUser);

        return ResponseEntity.ok(sparePartsDTOS);
    }
    @GetMapping("/{purchase_id}")
    public ModelAndView getPurchaseById(@PathVariable String purchase_id){
    	ModelAndView modelAndView=new ModelAndView();
        PurchaseDto byId = purchaseService.getById(purchase_id);
        return modelAndView;
    }
    @DeleteMapping("/{purchase_id}")
    public ModelAndView deleteById(@PathVariable String purchase_id){
    	ModelAndView modelAndView=new ModelAndView();
        purchaseService.deleteById(purchase_id);
        return modelAndView;
    }
    @PutMapping("/{purchase_id}")
    public ModelAndView update(@PathVariable String purchase_id,@RequestBody  CreatePurchaseDto createPurchaseDto,@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView=new ModelAndView();
        PurchaseDto purchaseDto = purchaseService.update(purchase_id,createPurchaseDto,domainUser);
        return modelAndView;
    }
    @GetMapping("/partPayment/updatePage/{id}")
    public ModelAndView updatePaymentPage(@AuthenticationPrincipal DomainUser domainUser, @PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        PurchaseDto purchaseDto=purchaseService.getById(id);
        modelAndView.addObject("purchase",purchaseDto);
        String reg;
        reg=domainUser.getRegion_name();
        modelAndView.addObject("reg", reg);
        return modelAndView;
    }
    @GetMapping("/partPayment/details/{id}")
    public ModelAndView PartPaymentDetails(@AuthenticationPrincipal DomainUser domainUser, @PathVariable String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("purchasePartPaymentDetails",purchaseService.getPartPaymentDetails(domainUser,id));
        PurchaseDto purchaseDto=purchaseService.getById(id);
        modelAndView.addObject("purchase",purchaseDto);
        String reg;
        reg=domainUser.getRegion_name();
        modelAndView.addObject("reg", reg);
        modelAndView.setViewName("PurchasePartPaymentDetailsPage");
        return modelAndView;
    }
    @PostMapping("/partPayment/update/{id}")
    public ModelAndView updatePartPayment(@AuthenticationPrincipal DomainUser domainUser,@ModelAttribute("purchase") PurchaseDto purchaseDto, @PathVariable String id)
    {
        purchaseService.updatePart(purchaseDto,id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/api/sales/partPayment/getIncome");
        return modelAndView;
    }

}
