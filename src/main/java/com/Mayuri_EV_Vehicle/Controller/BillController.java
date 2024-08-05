package com.Mayuri_EV_Vehicle.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Mayuri_EV_Vehicle.dto.BillDTO;
import com.Mayuri_EV_Vehicle.entity.Bill;
import com.Mayuri_EV_Vehicle.service.BillService;



@RestController
@RequestMapping("/bill")
public class BillController {

    private BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/create")
    public ResponseEntity<BillDTO> createBill(@RequestBody BillDTO billDTO) {
        BillDTO bill = billService.createBill(billDTO);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<Bill>> getBills(){
        List<Bill> bill = billService.getBill();
        return new ResponseEntity<>(bill,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getById(@PathVariable String id){
        Bill byId = billService.getById(id);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }
}
