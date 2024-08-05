package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.SalesPaymentDto;
import com.Mayuri_EV_Vehicle.entity.SalesPayment;
import com.Mayuri_EV_Vehicle.repository.SalesPaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SalesPaymentServiceImpl implements SalesPaymentService{
    private SalesPaymentRepository salesPaymentRepository;

    public SalesPaymentServiceImpl(SalesPaymentRepository salesPaymentRepository) {
        this.salesPaymentRepository = salesPaymentRepository;
    }

    @Override
    public SalesPaymentDto create(CreateSalesDto createSalesDto) {
//        SalesPayment salesPayment= new SalesPayment(UUID.randomUUID().toString(),createSalesDto.getSalesPrice(),
//                createSalesDto.getSales_totalPrice());
//        SalesPayment save = salesPaymentRepository.save(salesPayment);
        return null;
    }

    private SalesPaymentDto convertToDto(SalesPayment save) {
        SalesPaymentDto salesPaymentDto=new SalesPaymentDto(save.getId(),save.getSalesPrice(),save.getTotalPrice());
        return  salesPaymentDto;
    }
}
