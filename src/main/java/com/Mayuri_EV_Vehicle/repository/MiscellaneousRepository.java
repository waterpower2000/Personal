package com.Mayuri_EV_Vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.entity.Miscellaneous;
import com.Mayuri_EV_Vehicle.entity.Purchase;
import com.Mayuri_EV_Vehicle.entity.Sales;

public interface MiscellaneousRepository extends JpaRepository<Miscellaneous, String>{

}
