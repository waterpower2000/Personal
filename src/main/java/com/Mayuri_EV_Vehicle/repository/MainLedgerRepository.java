package com.Mayuri_EV_Vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Mayuri_EV_Vehicle.entity.MainLedger;
import com.Mayuri_EV_Vehicle.entity.Region;

public interface MainLedgerRepository extends JpaRepository<MainLedger, String> {

}
