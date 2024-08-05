package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EAutoVariantDetailsRepository extends JpaRepository<EAutoVariantDetails,String> {
//    EAutoVariantDetails findByengine_number(String engine_number);
}

