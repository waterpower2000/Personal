package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.config.DomainUser;
import com.Mayuri_EV_Vehicle.dto.CreatePurchaseDto;
import com.Mayuri_EV_Vehicle.dto.PurchaseDto;
import com.Mayuri_EV_Vehicle.dto.PurchasePartPaymentDTO;
import com.Mayuri_EV_Vehicle.entity.DashBoard;
import com.Mayuri_EV_Vehicle.entity.EAutoVariantDetails;
import com.Mayuri_EV_Vehicle.entity.Purchase;

import java.util.List;

public interface PurchaseService {
    PurchaseDto saveOnePurchase(CreatePurchaseDto createPurchaseDto,DomainUser domainUser);
    List<PurchaseDto> getAll();
    PurchaseDto getById(String purchaseId);
    void deleteById(String purchaseId);
    PurchaseDto update(String purchaseId,CreatePurchaseDto createPurchaseDto,DomainUser domainUser);
    
    public int deletePurchaseById(String id);
    
    public EAutoVariantDetails editPurchaseById(String id);
    
	List<Purchase> getAllPurchase(DomainUser domainUser);

    List<PurchaseDto> getAllPurchaseDetails(DomainUser domainUser);

    List<Purchase> getByRegion(String region, DashBoard dashBoard);
    
    byte[] downloadPurchaseData(String reg);

    void updatePart(PurchaseDto purchaseDto, String id);

    List<PurchaseDto> getAllFullPurchaseDetails(DomainUser domainUser);

    List<PurchasePartPaymentDTO> getPartPaymentDetails(DomainUser domainUser, String id);
}
