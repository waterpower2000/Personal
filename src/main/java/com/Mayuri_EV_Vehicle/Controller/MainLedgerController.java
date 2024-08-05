package com.Mayuri_EV_Vehicle.Controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.entity.MainLedger;
import com.Mayuri_EV_Vehicle.service.MainLedgerService;

@Controller
@RequestMapping("/api/mainLedger")
public class MainLedgerController {
	private MainLedgerService mainLedgerService;

	public MainLedgerController(MainLedgerService mainLedgerService) {
		super();
		this.mainLedgerService = mainLedgerService;
	}
	
	@GetMapping
	public List<MainLedger> getAll(@AuthenticationPrincipal DomainUser domainUser){
		List<MainLedger> allMainLedger = mainLedgerService.getAllMainLedger(domainUser);
		return allMainLedger;	
	}
}
