package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.SalesPaymentDto;

public interface SalesPaymentService {
    SalesPaymentDto create(CreateSalesDto createSalesDto);

}
