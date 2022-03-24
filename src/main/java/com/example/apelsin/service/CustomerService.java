package com.example.apelsin.service;
import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.entity.Customer;
import com.example.apelsin.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    final CustomerRepository customerRepository;
    public ApiResponse add(Customer customer) {
        customerRepository.save(customer);
        return new ApiResponse("Added", true);
    }

    public ApiResponse edit(Integer id, Customer customer) {

        Optional<Customer> byId = customerRepository.findById(id);
        if (byId.isEmpty()) return new ApiResponse(" Not found!", false);
        Customer edited = byId.get();
        edited.setName(customer.getName());
        edited.setAddress(customer.getAddress());
        edited.setCountry(customer.getCountry());
        edited.setPhone(customer.getPhone());
        customerRepository.save(edited);
        return new ApiResponse("Edited", true);
    }
}
