package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.PurchasePaymentDto;

public interface PurchasePayementService {
    PurchasePaymentDto save(CreatePurchaseDto createPurchaseDto);
}
