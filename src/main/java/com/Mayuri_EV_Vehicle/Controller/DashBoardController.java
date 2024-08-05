package com.Mayuri_EV_Vehicle.Controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.entity.DashBoard;
import com.Mayuri_EV_Vehicle.service.DashBoardService;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController {
	private DashBoardService dashBoardService;

    
	public DashBoardController(DashBoardService dashBoardService) {
		this.dashBoardService = dashBoardService;
	}
	
    @PostMapping("/")
    public ModelAndView getAll(@ModelAttribute("model") DashBoard dashBoard ,@AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView = new ModelAndView();
    	List<?> create = dashBoardService.create(dashBoard,domainUser);
    	modelAndView.addObject("create", create);
    	modelAndView.setViewName("index");
    	return modelAndView;
    }
    
}
