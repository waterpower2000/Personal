package com.Mayuri_EV_Vehicle.repository;


import com.Mayuri_EV_Vehicle.entity.PartPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartPaymentRepositoty extends JpaRepository<PartPayment,String> {
    List<PartPayment> findAllBySales_id(String sales_id);
}
