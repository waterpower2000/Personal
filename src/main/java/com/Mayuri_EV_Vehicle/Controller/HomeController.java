package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.EAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.SparePartsVariantDetailsDTO;
import com.Mayuri_EV_Vehicle.entity.VechileType;
import com.Mayuri_EV_Vehicle.service.EAutoVariantDetailsService;
import com.Mayuri_EV_Vehicle.service.EAutoVariantsService;
import com.Mayuri_EV_Vehicle.service.SparePartsVariantDetailsService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/api")
public class HomeController {

    private final EAutoVariantsService eAutoVariantsService;
    private final EAutoVariantDetailsService eAutoVariantDetailsService;
    private final SparePartsVariantDetailsService sparePartsVariantDetailsService;

    public HomeController(EAutoVariantsService eAutoVariantsService,EAutoVariantDetailsService eAutoVariantDetailsService, SparePartsVariantDetailsService sparePartsVariantDetailsService) {
        this.eAutoVariantsService = eAutoVariantsService;
        this.eAutoVariantDetailsService= eAutoVariantDetailsService;
        this.sparePartsVariantDetailsService = sparePartsVariantDetailsService;
    }

    @GetMapping("/logout")
    public String fetchSignoutSite(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login.html";
    }

    @GetMapping("/")
    public ModelAndView getIndex(@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
		List<EAutoVariantDetailsDto> eAutoVariantDetails = eAutoVariantDetailsService.getFilterVariants(domainUser);
		List<EAutoVariantDetailsDto> eAutoList = eAutoVariantDetails.stream().filter(ev -> ev.getType().equals(VechileType.E_AUTO.name())).collect(Collectors.toList());
		List<EAutoVariantDetailsDto> eRikshawList = eAutoVariantDetails.stream().filter(ev -> ev.getType().equals(VechileType.E_RICKSHAW.name())).collect(Collectors.toList());
		List<SparePartsVariantDetailsDTO> sparePartsList = sparePartsVariantDetailsService.getAllSpareVariantDetails();
		String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
		modelAndView.addObject("eAutoList", eAutoList);
		modelAndView.addObject("eRikshawList", eRikshawList);
		modelAndView.addObject("spareParts", sparePartsList);
		
		modelAndView.setViewName("inventry-management");
		return modelAndView;
    }
    @GetMapping("/home/inventry-management")
    public ModelAndView inventrymanagement(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("inventry-management");
        return mv;
    }
    
    @GetMapping("/home/purchase")
    public ModelAndView purchase(@ModelAttribute("purchase") CreatePurchaseDto createPurchaseDto, @AuthenticationPrincipal DomainUser domainUser)
    {
        ModelAndView mv = new ModelAndView();
        mv.addObject("purchase", new CreatePurchaseDto());
        String reg;
		reg=domainUser.getRegion_name();
		mv.addObject("reg", reg);
        mv.addObject("variants", eAutoVariantsService.getAllVariants(domainUser));
        mv.setViewName("purchase");
        return mv;
    }
    
    @GetMapping("/home/sales")
    public ModelAndView sales(@AuthenticationPrincipal DomainUser domainUser){
        ModelAndView mv = new ModelAndView();
        mv.addObject("createSalesDto", new CreateSalesDto());
        String reg;
		reg=domainUser.getRegion_name();
		mv.addObject("reg", reg);
        mv.addObject("variants", eAutoVariantDetailsService.getAllVariants(domainUser));
        mv.setViewName("sales");
        return mv;
    }
    @GetMapping("/home/dashboard")
    public ModelAndView dashboard(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
    
    @GetMapping("/home/account")
    public ModelAndView account(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("accounts");
        return mv;
    }

    @GetMapping("/home/user-management")
    public ModelAndView user_management(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user-management");
        return mv;
    }
    @GetMapping("/home/region-management")
    public ModelAndView region_management(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("region-management");
        return mv;
    }
    
    @GetMapping("/home/create-user")
    public ModelAndView add_user(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("add-user");
        return mv;
    }
}
