package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String> {
	
	
}
