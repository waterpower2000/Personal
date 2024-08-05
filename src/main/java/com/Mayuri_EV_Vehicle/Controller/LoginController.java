package com.Mayuri_EV_Vehicle.Controller;

import com.Mayuri_EV_Vehicle.dto.MessageDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RequestMapping("/")
@Controller
public class LoginController {

    @RequestMapping(value = "/")
    public String getIndex() {
        return "redirect:/api/";
    }

    @RequestMapping(value = "/app")
    public String getAppIndex() {
        return "redirect:/api/";
    }

    @RequestMapping(value = "/login.html")
    public String getLogin() {
        return "login";
    }

//    @RequestMapping(value = "/performLogin.html")
//    public ModelAndView faliureLogin(HttpSession session)
//    {
//        ModelAndView modelAndView = new ModelAndView();
//        session.setAttribute("message", new MessageDTO("Invalid Cridentials", "Failure"));
//        modelAndView.addObject("message", "Invalid Cridentials");
//        modelAndView.setViewName("login");
//        return modelAndView;
//    }

}
