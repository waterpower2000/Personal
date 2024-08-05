package com.Mayuri_EV_Vehicle.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice
public class Exception_handeler {

//    @ExceptionHandler(value = NullPointerException.class)
//    public ModelAndView nullpointerexceptionhandeler()
//    {
//        ModelAndView modelAndView=new ModelAndView();
//        modelAndView.addObject("msg","Null Pointer Exception Occurs!... Check the Input fields, Some of them may not be NULL");
//        modelAndView.setViewName("exception-handeler-page");
//        return modelAndView;
//    }
//    @ExceptionHandler(value = NumberFormatException.class)
//    public ModelAndView numberformatexceptionhandeler()
//    {
//        ModelAndView modelAndView=new ModelAndView();
//        modelAndView.addObject("msg","Number Format Exception Occurs!... Check the Input fields");
//        modelAndView.setViewName("exception-handeler-page");
//        return modelAndView;
//    }
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView globalexceptionhandeler()
//    {
//        ModelAndView modelAndView=new ModelAndView();
//        modelAndView.addObject("msg","Exception Occurs!... Check The Input Fields, And Give Proper Inputs");
//        modelAndView.setViewName("exception-handeler-page");
//        return modelAndView;
//    }

}
