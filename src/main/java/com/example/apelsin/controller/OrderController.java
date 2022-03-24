package com.example.apelsin.controller;

import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.OrderDto;
import com.example.apelsin.entity.Order;
import com.example.apelsin.repository.CustomerRepository;
import com.example.apelsin.repository.OrderRepository;
import com.example.apelsin.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {


    final OrderRepository orderRepository;
    final CustomerRepository customerRepository;
    final OrderService orderService;
    @GetMapping
    public HttpEntity<?> getAll() {
        List<Order> all = orderRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Order> byId = orderRepository.findById(id);
        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Order()));
    }
    @PostMapping("/{id}")
    public HttpEntity<?> add(@RequestBody OrderDto dto) {
        ApiResponse response = orderService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody OrderDto orderDto) {
        ApiResponse response = orderService.edit(id, orderDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Order order = byId.get();
        order.setActive(false);
        orderRepository.save(order);
        return ResponseEntity.ok().body("DELETED!");
    }
    @GetMapping("/byCustId/{id}")
    public HttpEntity<?> getAllByCust(@PathVariable Integer id) {
        return ResponseEntity.ok().body(orderRepository.findAllByCustIdActive(id));
    }
    @GetMapping("/orders_without_details")
    public HttpEntity<?> getOrders_without_details() {
        List<Order> all = orderRepository.getOrders_without_details();
        return ResponseEntity.ok().body(all);
    }
    @GetMapping("/orders_without_invoices")
    public HttpEntity<?> getOrders_without_invoices() {
        List<Order> all = orderRepository.getOrders_without_invoices();
        return ResponseEntity.ok().body(all);
    }



}
