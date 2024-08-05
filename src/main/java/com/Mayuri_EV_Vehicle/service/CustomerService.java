package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);

    List<CustomerDto> getAll();

    CustomerDto getById(String id);

    CustomerDto saveCustomer(CreateSalesDto createSalesDto);
}
