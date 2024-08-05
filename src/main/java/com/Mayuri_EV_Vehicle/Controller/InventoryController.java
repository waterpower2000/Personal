package com.Mayuri_EV_Vehicle.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.Mayuri_EV_Vehicle.dto.EAutoVariantDetailsDto;
import com.Mayuri_EV_Vehicle.dto.SparePartsVariantDetailsDTO;
import com.Mayuri_EV_Vehicle.entity.SparePartsVariantDetails;
import com.Mayuri_EV_Vehicle.entity.VechileType;
import com.Mayuri_EV_Vehicle.service.EAutoVariantDetailsService;
import com.Mayuri_EV_Vehicle.service.SparePartsVariantDetailsService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.entity.InventoryDetails;
import com.Mayuri_EV_Vehicle.service.InventoryService;

@Controller
@RequestMapping("/api/inventory")
public class InventoryController {
	
	private final InventoryService inventoryService;
	private final EAutoVariantDetailsService eAutoVariantDetailsService;
	private final SparePartsVariantDetailsService sparePartsVariantDetailsService;

	public InventoryController(InventoryService inventoryService, EAutoVariantDetailsService eAutoVariantDetailsService, SparePartsVariantDetailsService sparePartsVariantDetailsService) {
		this.inventoryService = inventoryService;
		this.eAutoVariantDetailsService = eAutoVariantDetailsService;
		this.sparePartsVariantDetailsService = sparePartsVariantDetailsService;
	}

	@GetMapping("/get")
	public ModelAndView getInventoryDetails(@AuthenticationPrincipal DomainUser domainUser){
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
//	@GetMapping("/page/{pageno}")
//	public void findpagenate( @PathVariable("pageno") int pageno,@AuthenticationPrincipal DomainUser domainUser)
//	{
//		int pagesize=5;
//		Page<EAutoVariantDetailsDto> page=eAutoVariantDetailsService.getpaginated(pageno,pagesize,domainUser);
//		List<EAutoVariantDetailsDto> eAutoVariantDetails=page.getContent();
//
//	}

	@GetMapping()
	public ModelAndView getInventoryLedgerByDate() {
		ModelAndView modelAndView = new ModelAndView();
		
		return modelAndView;
	}
}
