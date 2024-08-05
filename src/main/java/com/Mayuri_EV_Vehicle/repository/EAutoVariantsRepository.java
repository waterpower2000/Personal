package com.Mayuri_EV_Vehicle.repository;


import com.Mayuri_EV_Vehicle.entity.EAuto;
import com.Mayuri_EV_Vehicle.entity.EAutoVariants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EAutoVariantsRepository extends JpaRepository<EAutoVariants,String>{
}
