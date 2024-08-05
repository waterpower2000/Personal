package com.Mayuri_EV_Vehicle.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Mayuri_EV_Vehicle.dto.BillDTO;
import com.Mayuri_EV_Vehicle.entity.Bill;
import com.Mayuri_EV_Vehicle.repository.BillRepository;


@Service
public class BillServiceImpl implements BillService{
    private BillRepository billRepository;

    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public BillDTO createBill(BillDTO billDTO) {
        Bill bill=new Bill();
        bill.setId(UUID.randomUUID().toString());
        bill.setBillNumber(billDTO.getBillNumber());
        bill.setBillDate(billDTO.getBillDate());
        bill.setAmount(billDTO.getAmount());
        Bill save = billRepository.save(bill);
        return convertToDto(save);
    }

    @Override
    public Bill create(BillDTO billDTO) {
        Bill bill=new Bill();
        bill.setId(UUID.randomUUID().toString());
        bill.setBillNumber(billDTO.getBillNumber());
        bill.setBillDate(billDTO.getBillDate());
        bill.setAmount(billDTO.getAmount());
        Bill save = billRepository.save(bill);
        return save;
    }

    @Override
    public List<Bill> getBill() {
        List<Bill> all = billRepository.findAll();
        return all;
    }

    @Override
    public Bill getById(String id) {
        Optional<Bill> byId = billRepository.findById(id);
        return byId.get();
    }

    private BillDTO convertToDto(Bill save) {
        BillDTO billDTO=new BillDTO();
        billDTO.setId(save.getId());
        billDTO.setBillNumber(save.getBillNumber());
        billDTO.setAmount(save.getAmount());
        billDTO.setBillDate(save.getBillDate());
        return billDTO;
    }
}

