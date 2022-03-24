package com.example.apelsin.service;
import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.OrderDto;
import com.example.apelsin.entity.Customer;
import com.example.apelsin.entity.Order;
import com.example.apelsin.repository.CustomerRepository;
import com.example.apelsin.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    public ApiResponse add(OrderDto dto) {
        Order order = new Order();
        Optional<Customer> optionalCustomer = customerRepository.findById(dto.getCustId());
        if (optionalCustomer.isEmpty()) return new ApiResponse("Not found!", false);
        order.setCustId(optionalCustomer.get());
        order.setDate(dto.getDate());
        return new ApiResponse("Added", true);
    }

    public ApiResponse edit(Integer id, OrderDto dto) {

        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()) return new ApiResponse(" Not found!", false);
        Order order = byId.get();

        order.setDate(dto.getDate());
        Optional<Customer> optionalGM = customerRepository.findById(dto.getCustId());
        order.setCustId(optionalGM.get());
        orderRepository.save(order);
        return new ApiResponse("Edited", true);
    }

}
