package com.Mayuri_EV_Vehicle.service;

import java.util.List;

import com.Mayuri_EV_Vehicle.dto.BillDTO;
import com.Mayuri_EV_Vehicle.entity.Bill;


public interface BillService {
    BillDTO createBill(BillDTO billDTO);
    Bill create(BillDTO billDTO);

    List<Bill> getBill();

    Bill getById(String id);
}
