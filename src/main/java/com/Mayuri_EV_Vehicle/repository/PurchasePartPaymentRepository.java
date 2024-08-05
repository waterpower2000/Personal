package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.PartPayment;
import com.Mayuri_EV_Vehicle.entity.PurchasePartPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchasePartPaymentRepository extends JpaRepository<PurchasePartPayment,String> {

    List<PurchasePartPayment> findAllByPurchase_id(String purchase_id);

}
