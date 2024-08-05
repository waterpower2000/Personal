package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateMiscellaneousHeads;
import com.Mayuri_EV_Vehicle.dto.MessageDTO;
import com.Mayuri_EV_Vehicle.service.MiscellaneousHeadsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;



@Controller
@RequestMapping("/api/miscellaneousHeads")
public class MiscellaneousHeadsController {
    private MiscellaneousHeadsService miscellaneousHeadsService;
    public MiscellaneousHeadsController(MiscellaneousHeadsService miscellaneousHeadsService)
    {
        this.miscellaneousHeadsService=miscellaneousHeadsService;
    }

    @PostMapping("/save")
    public ModelAndView saveheads(@ModelAttribute("createMiscellaneousHeads")CreateMiscellaneousHeads createMiscellaneousHeadsDto, @AuthenticationPrincipal DomainUser domainUser, HttpSession session)
    {
        ModelAndView modelAndView = new ModelAndView();
        if(miscellaneousHeadsService.saveMiscellaneousHeads(createMiscellaneousHeadsDto,domainUser)==null)
        {
            session.setAttribute("message", new MessageDTO("Transaction Name Can't be Duplicate", "Success"));
            modelAndView.addObject("message", "Transaction Name Can't be Duplicate");
        }
        else {
            session.setAttribute("message", new MessageDTO("Entry added Successfully", "Success"));
            modelAndView.addObject("message", "Entry added Successfully");
        }
        modelAndView.setViewName("redirect:/api/miscellaneous/get");
        return modelAndView;
    }

}
