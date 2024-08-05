package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreateERikshaDto;
import com.Mayuri_EV_Vehicle.dto.EAutoDto;
import com.Mayuri_EV_Vehicle.dto.ERikshaDto;
import com.Mayuri_EV_Vehicle.service.ERikshaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/api/eriksha")
public class ERikshaController {
    private ERikshaService eRikshaService;

    public ERikshaController(ERikshaService eRikshaService) {
        this.eRikshaService = eRikshaService;
    }
    @PostMapping
    public ModelAndView saveERiksha(@RequestBody CreateERikshaDto createERikshaDto, @AuthenticationPrincipal DomainUser domainUser){
    	ModelAndView modelAndView=new ModelAndView();
        ERikshaDto eRiksha = eRikshaService.createERiksha(createERikshaDto,domainUser);
        return modelAndView;
    }
//    @GetMapping
//    public ResponseEntity<List<ERikshaDto>> getAllERiksha(){
//        List<ERikshaDto> all =eRikshaService.getAll();
//        return new ResponseEntity<>(all, HttpStatus.OK);
//    }
    @GetMapping("/getall")
    public ModelAndView getallERiksha(@AuthenticationPrincipal DomainUser domainUser){
        ModelAndView modelAndView=new ModelAndView();
        List<ERikshaDto> all = eRikshaService.getAll(domainUser);
        modelAndView.addObject("allERiksha", all);
        modelAndView.addObject("message", "All E-Riksha List");
        modelAndView.setViewName("redirect:/api/eriksha/getall");
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getERikshaById(@PathVariable String id){
    	ModelAndView modelAndView=new ModelAndView();
        ERikshaDto eRikshaById = eRikshaService.getERikshaById(id);
        return modelAndView;
    }
    @PutMapping("/{id}")
    public ModelAndView updateERiksha(@PathVariable String id,@RequestBody CreateERikshaDto createERikshaDto) {
    	ModelAndView modelAndView=new ModelAndView();
        ERikshaDto updatedERikshaDTO = eRikshaService.updateERiksha(id,createERikshaDto);
        return modelAndView;
    }
    @DeleteMapping("/{id}")
    public ModelAndView deleteERiksha(@PathVariable String id) {
    	ModelAndView modelAndView=new ModelAndView();
        eRikshaService.deleteERiksha(id);
        return modelAndView;
    }
}
