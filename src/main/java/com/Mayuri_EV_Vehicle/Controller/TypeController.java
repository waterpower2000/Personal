package com.Mayuri_EV_Vehicle.Controller;
import com.Mayuri_EV_Vehicle.dto.TypeDto;
import com.Mayuri_EV_Vehicle.entity.TypeList;
import com.Mayuri_EV_Vehicle.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/vehicle")
    public ResponseEntity<TypeList> getVehicleType() {
        return ResponseEntity.ok(TypeList.Vehicle);
    }
    @GetMapping("/spare-parts")
    public ResponseEntity<TypeList> getSparePartsType() {
        return ResponseEntity.ok(TypeList.Spare_Parts);
    }

    @PostMapping("/save")
    public ModelAndView saveType(@RequestBody TypeDto typeDto) {
    	ModelAndView modelAndView=new ModelAndView();
        try {
            TypeDto typeDto1 = typeService.saveType(typeDto);
            return modelAndView;
        } catch (IllegalArgumentException e) {
            return modelAndView;
        } catch (Exception e) {
            return modelAndView;
        }
    }
    @GetMapping("/{id}")
    public ModelAndView getType(@PathVariable String id){
    	ModelAndView modelAndView=new ModelAndView();
        TypeDto typeById = typeService.getTypeById(id);
        return modelAndView;
    }
    @GetMapping
    public ModelAndView getType(){
    	ModelAndView modelAndView=new ModelAndView();
        List<TypeDto> allType = typeService.getAllType();
        return modelAndView;
    }
}
