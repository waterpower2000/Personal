package com.Mayuri_EV_Vehicle.repository;

import com.Mayuri_EV_Vehicle.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String> {
}
