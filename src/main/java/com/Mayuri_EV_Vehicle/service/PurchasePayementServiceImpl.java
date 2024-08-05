package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.PurchasePaymentDto;
import com.Mayuri_EV_Vehicle.entity.PurchasePayement;
import com.Mayuri_EV_Vehicle.repository.PurchasePaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchasePayementServiceImpl implements PurchasePayementService{
    private PurchasePaymentRepository purchasePaymentRepository;

    public PurchasePayementServiceImpl(PurchasePaymentRepository purchasePaymentRepository) {
        this.purchasePaymentRepository = purchasePaymentRepository;
    }

    @Override
    public PurchasePaymentDto save(CreatePurchaseDto createPurchaseDto) {
//        PurchasePayement purchasePayement=new PurchasePayement(UUID.randomUUID().toString(),
//                createPurchaseDto.getPurchasePrice(),createPurchaseDto.getTotalPrice());
//        PurchasePayement save = purchasePaymentRepository.save(purchasePayement);
//        return convertToDto(save);
        return null;
    }

    private PurchasePaymentDto convertToDto(PurchasePayement save) {
        PurchasePaymentDto purchasePaymentDto=new PurchasePaymentDto(save.getId(),save.getPurchasePrice(),save.getTotalPrice());
        return purchasePaymentDto;
    }
}
