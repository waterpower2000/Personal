package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.CustomerDto;
import com.Mayuri_EV_Vehicle.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/api/customer")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping
    public ResponseEntity<CustomerDto> create(@RequestBody CustomerDto customerDto){
        CustomerDto save = customerService.save(customerDto);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }
    @PostMapping("/")
    public ModelAndView create(@RequestBody CreateSalesDto createSalesDto){
    	ModelAndView modelAndView=new ModelAndView();
        CustomerDto save = customerService.saveCustomer(createSalesDto);
        return modelAndView;
    }
    @GetMapping
    public ModelAndView getAll(){
    	ModelAndView modelAndView=new ModelAndView();
        List<CustomerDto> all = customerService.getAll();
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView getAll(@PathVariable String id){
    	ModelAndView modelAndView=new ModelAndView();
        CustomerDto byId = customerService.getById(id);
        return modelAndView;
    }
}
