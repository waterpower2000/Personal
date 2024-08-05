package com.Mayuri_EV_Vehicle.service;

import com.Mayuri_EV_Vehicle.dto.CreateSalesDto;
import com.Mayuri_EV_Vehicle.dto.CustomerDto;
import com.Mayuri_EV_Vehicle.entity.Customer;
import com.Mayuri_EV_Vehicle.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer=new Customer(UUID.randomUUID().toString(), customerDto.getCustomer_name(), customerDto.getCustomer_number(),
                customerDto.getCustomer_AadhaarCard(), customerDto.getCustomer_PanCard(), customerDto.getCustomer_GSTNumber(),
                customerDto.getCustomer_address());
        Customer save = customerRepository.save(customer);
        return convertToDto(save);
    }

    @Override
    public List<CustomerDto> getAll() {
        List<Customer> all = customerRepository.findAll();
        List<CustomerDto> allCustomerDto= new ArrayList<>();
        for(Customer customer: all){
            CustomerDto customerDto = convertToDto(customer);
            allCustomerDto.add(customerDto);
        }
        return allCustomerDto;
    }

    @Override
    public CustomerDto getById(String id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return convertToDto(byId.get());
    }

    @Override
    public CustomerDto saveCustomer(CreateSalesDto createSalesDto) {
        Customer customer=new Customer(UUID.randomUUID().toString(), createSalesDto.getSales_customer_name(), createSalesDto.getSales_customer_number(),
                createSalesDto.getSales_customer_AadhaarCard(), createSalesDto.getSales_customer_PanCard(), createSalesDto.getSales_customer_GSTNumber(),
                createSalesDto.getSales_customer_address());
        Customer save = customerRepository.save(customer);
        return convertToDto(save);
    }

    private CustomerDto convertToDto(Customer save) {
        CustomerDto customerDto=new CustomerDto(save.getId(), save.getCustomer_name(),save.getCustomer_number(),
                save.getCustomer_AadhaarCard(),save.getCustomer_PanCard(), save.getCustomer_GSTNumber(), save.getCustomer_address());
        return customerDto;
    }
}
