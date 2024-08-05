package com.Mayuri_EV_Vehicle.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateMiscellaneousDto;
import com.Mayuri_EV_Vehicle.dto.CreateStockTransferDto;
import com.Mayuri_EV_Vehicle.dto.MessageDTO;
import com.Mayuri_EV_Vehicle.dto.RegionDto;
import com.Mayuri_EV_Vehicle.service.RegionService;
import com.Mayuri_EV_Vehicle.service.StockService;


@Controller
@RequestMapping("/api/stock")
public class StockController {
	private StockService stockService;
	private RegionService regionService;
	
	public StockController(StockService stockService,RegionService regionService)
	{
		this.stockService = stockService;
		this.regionService = regionService;
	}
	
	@GetMapping("/get")
	public ModelAndView getMiscellaneousExpense(@AuthenticationPrincipal DomainUser domainUser)
	{
		ModelAndView modelAndView = new ModelAndView();
		List<RegionDto> allRegion = regionService.getAllRegionDetail();
		String reg;
		reg=domainUser.getRegion_name();
		modelAndView.addObject("reg", reg);
		 modelAndView.addObject("allRegion",allRegion);
		modelAndView.addObject("createStockDto",new CreateStockTransferDto());
		modelAndView.setViewName("stock-transfer");
		return modelAndView;
	}
	
	
	@PostMapping("/save")
    public ModelAndView savePuchase(@Valid @ModelAttribute("createStockDto")CreateStockTransferDto createStockTransferDto, @AuthenticationPrincipal DomainUser domainUser, HttpSession session, BindingResult result, Errors errors){
        if(errors.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("redirect:/api/stock/get");
			return modelAndView;
		}
		else {
			ModelAndView modelAndView = new ModelAndView();
			stockService.changeRegion(createStockTransferDto, domainUser);
			String reg;
			reg = domainUser.getRegion_name();
			modelAndView.addObject("reg", reg);
			session.setAttribute("message", new MessageDTO("Stock Transferred Successfully", "Success"));
			modelAndView.addObject("message", "Stock Transferred Successfully");
			modelAndView.setViewName("redirect:/api/stock/get");
			return modelAndView;
		}
    }

}
