package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.EAuto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EAutoRepository extends JpaRepository<EAuto,String> {
    Optional<List<EAuto>> findByVehicleId(String vehicleId);
    EAuto findByname(String name);
    void deleteByName(String name);
}
