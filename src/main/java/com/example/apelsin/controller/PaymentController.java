package com.example.apelsin.controller;

import com.example.apelsin.payload.ApiResponse;
import com.example.apelsin.payload.PaymentDto;
import com.example.apelsin.entity.Payment;
import com.example.apelsin.repository.InvoiceRepository;
import com.example.apelsin.repository.PaymentRepository;
import com.example.apelsin.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    final PaymentRepository paymentRepository;
    final InvoiceRepository invoiceRepository;
    final PaymentService paymentService;
    @GetMapping
    public HttpEntity<?> getAll() {
        List<Payment> all = paymentRepository.findAllByActiveTrue();
        return ResponseEntity.ok().body(all);
    }
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Integer id) {
        Optional<Payment> byId = paymentRepository.findById(id);
        return ResponseEntity.status(byId.isEmpty() ?
                HttpStatus.NOT_FOUND : HttpStatus.OK).body(byId.orElse(new Payment()));
    }
    @PostMapping
    public HttpEntity<?> add(@RequestBody PaymentDto dto) {
        ApiResponse response = paymentService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id, @RequestBody PaymentDto paymentDto) {
        ApiResponse response = paymentService.edit(id, paymentDto);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id) {
        Optional<Payment> byId = paymentRepository.findById(id);
        if (byId.isEmpty()) return ResponseEntity.status(404).body("NOT FOUND");
        Payment payment = byId.get();
        payment.setActive(false);
        paymentRepository.save(payment);
        return ResponseEntity.ok().body("DELETED!");
    }


}
