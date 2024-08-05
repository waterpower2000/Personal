package com.Mayuri_EV_Vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.entity.Bill;



public interface BillRepository extends JpaRepository<Bill, String> {
}
