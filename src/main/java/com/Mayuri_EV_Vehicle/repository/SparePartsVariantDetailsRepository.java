package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.SparePartsVariantDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SparePartsVariantDetailsRepository extends JpaRepository<SparePartsVariantDetails, String> {
}
