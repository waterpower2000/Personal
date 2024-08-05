package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.CreateUserManagmentDto;
import com.Mayuri_EV_Vehicle.dto.UserManagmentDto;
import com.Mayuri_EV_Vehicle.service.UserManagmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/usermanagment")
public class UserManagmentController {
    private UserManagmentService userManagmentService;

    public UserManagmentController(UserManagmentService userManagmentService) {
        this.userManagmentService = userManagmentService;
    }

    @PostMapping("/")
    public ModelAndView createUser(@ModelAttribute("createUserManagmentDto") CreateUserManagmentDto createUserManagmentDto){
        ModelAndView modelAndView=new ModelAndView();
        UserManagmentDto user = userManagmentService.createUser(createUserManagmentDto);
        return modelAndView;
    }


}
